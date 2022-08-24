# Validação do modelo com Bean Validation

- Bean Validation faz parte do JEE e tem como objetivo realizar a validação dos objetos
- Adicionamos anotações nas classes de modelos para definir as restrições
- Para utilizar, basta adicionarmos a depedencia no arquivo `pom.xml`

```properties
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
            <version>2.7.2</version>
        </dependency>
```

## Adiconando constraints e validando no controller com `@Valid`

- Na entidade anotamos as validações, por exemplo não aceitar valores nulos em uma propriedade

```java
    @Column(nullable = false)
    @NotNull
    private String nome;
```

- No controller, colocamos a anotação `@Valid` para validar a entidade também na requisição

```java
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return cadastroRestauranteService.salvar(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
```

## Customizando mensagem de erros das validações

- A exceção lançada quando ocorre algum erro de validação é o `MethodArgumentNotValidException` 
- Exemplo de customização da mensagem

```java@Override
protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        
    Problem problem = createProblemBuilder(status, problemType, detail)
        .userMessage(detail)
        .build();
    
    return handleExceptionInternal(ex, problem, headers, status, request);
}  
```

## Algumas constraints do Bean Validation

### `@DecimalMin("1")`

- Faz a validação de valor minimo decimal
- Também podemos anotar com `@PositiveOrZero` para não permitir valores negativos 

```java
    @Column(nullable = false)
    @DecimalMin("1")
    private BigDecimal taxaFrete;
```

### `@NotEmpty` e `@NotBlank`

- Não aceita valores vazios para strings e espaçamento em branco
- Alternativa ao `@NotNull` pois realiza também a validação de valores nulos

```java
    @Column(nullable = false)
    @NotEmpty
    private String nome;
```

### Mais anotações do BeanValidation

- As anotações de validações podem ser consultadas na documentação:
- https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-builtin-constraints

## Validando as associações de uma entidade em cascata

- Podemos adicionar a validação `@NotNull` em uma propriedade que é compartilhada com outras entidades (Relacionamento entre as tabelas)
- Valida as propriedades de cozinha

```java
    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cozinha cozinha;
```

## Agrupando e restrigindo constraints que devem ser usadas na validação

- Em alguns casos queremos realizar as validações dos campos somente em determinadas condições ou quando alguma outra entidade está relacionada
- Isso pode ser útil quando temos validações em cascatas
- A entidade cozinha possui uma validação de id que nao pode ser nula, somente é usada quando temos atualização de restaurante por exemplo
- Nessa situação pode ser interessante agrupar a validação
- Criamos uma interface que será usada para agrupar as validações

```java
public interface Groups {

    public interface CadastroRestaurante {}
}

```

- Quando for cadastrar um restaurante valida um objeto do tipo Restaurante que possui constraints do grupo restaurante
- Todas as propriedades de Restaurantes deverá ter o grupo cadastrado

```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Groups.CadastroRestaurante.class)
    private Long id;
```

- Mas para aplicar os efeitos de validação dos grupos é necessário alterar o `@Valid` do controller para `@Validate` para que seja possível validar os grupos

```java
@Validated(Groups.CadastroRestaurante.class)
```

- Uma alternativa de validar essas constraints é utilizar a validação em cascata com `@ConvertGroup`
- Na classe restaurante na propriedade que tem a propriedade de Cozinha, usamos o `@ConvertGroup`

```java
    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    private Cozinha cozinha;
```

- Na classe de Cozinha usamos o grupo de cadastro Restaurante

```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Groups.CadastroRestaurante.class)
    private Long id;
```

- No controller usamos somente o `@Valid` ao invés de usar o `@Validated`

## Customizando e resolvendo mensagens de validação globais em Resource Bundle

- Criamos um arquivo de propriedades que conterá as mensagens de erro dos atributos `messages.properties`
- No exemplo abaixo caso nao for informado o nome do restaurante será apresentado a mensagem
- Essa é uma especificação do spring

```properties
NotBlank.restaurante.nome=Nome do restaurante é obrigatório
```

- Também é possível criar uma validação genérica para todos os nomes, independente da entidade

```properties
NotBlank.nome="Nome deve ser obrigatório"
```

## Resolvendo mensagens de validação com Resource Bundle do Bean Validation

- Especificação do bean validation
- Por padrão as mensagens de erro (inglês) vem do bean validation, porém podemos customizar as mensagens
- Criamos um arquivo `ValidationMessages.properties` que conterá as constraints do bean validation

```properties
javax.validation.constraints.PositiveOrZero.message=deve ser um número positivo
```
- O arquivo `messages.properties` sobscreve o arquivo `ValidationMessages.properties`

## Usando o Resource Bundle do Spring como Resource Bundle do Bean Validation

- Podemos juntar os dois arquivos `messages.properties` e o `ValidationMessages.properties` para evitar problemas de sobrescrita
- Criamos uma classe de configuração que usará o `messages.properties` como padrão das mensagens de erro do bean validator

```java
@Configuration
public class ValidationConfig {

    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
```

## Criando constraints de validação customizadas usando Composição

- Podemos criar nossas próprias constraints de validação
- Criamos uma anotação `TaxaFrete`

```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@PositiveOrZero
public @interface TaxaFrete {
    
    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    String message() default "{TaxaFrete.invalida}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

- Na entidade que é chamado atributo `taxaFrete` usamos anotação que criamos

```java
    @Column(nullable = false)
    @DecimalMin(value = "1")
    @TaxaFrete
    private BigDecimal taxaFrete;
```

## Criando constraints de validação customizadas com implementação de ConstraintValidator

- É possível criar constraint que possui interfaces próprias de validação. Pode ser útil em casos que precisamos validar algo necessário da regra de negócios
- Criamos uma anotação `Multiplo` que utilizará o `MultiploValidator` como validação

```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MultiploValidator.class})
@PositiveOrZero
public @interface Multiplo {

    String message() default "{Multiplo valor inválida}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int numero();
}
```

- Criamos a classe que implementará a constraint de validação do bean validator

```java
public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        if (number != null) {
            var valorDecimal = BigDecimal.valueOf(number.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(numeroMultiplo);
            var resto = valorDecimal.remainder(multiploDecimal);
            valid = BigDecimal.ZERO.compareTo(resto) == 0;
        }
        return valid;
    }
}
```

## Criando validação em nível de classe

- Usado quando temos uma regra que depende de 2 ou mais propriedades
- Validação condicional dependendo da primeira propriedade
- Na classe `Restaurante` usamos a anotação `ValorZeroIncluiDescricao`que contem uma regra de validação de duas propriedades

```java
@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Entity
public class Restaurante {
}
```

- `ValorZeroIncluiDescricao` possui anotação que define que ela deve ser utilizada somente em classe ao invés de propriedade

```java
@Target({ElementType.TYPE})
```

- A classe de validaçao fica com a seguinte regra

```java
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;
        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(o.getClass(), valorField)
                    .getReadMethod().invoke(o);
            String descricao = (String) BeanUtils.getPropertyDescriptor(o.getClass(), descricaoField)
                    .getReadMethod().invoke(o);
            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
            }
        } catch (Exception e) {
               throw new ValidationException(e);
        }
        return valido;
    }
```
