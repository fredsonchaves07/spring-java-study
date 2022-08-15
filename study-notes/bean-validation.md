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