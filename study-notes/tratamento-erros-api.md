# Tratamento e modelagem de erros da API

## Lançamento de erro com `@ResponseStatus`

- Podemos tratar os erros da api sem a necessidade do `try catch` nos controladores
- Anotamos a classe de exceção com o `@ResponseStatus`

```java

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entidade nao encontrada")
public class EntidadeNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
```

## Lançamento de erro com `ResponseStatusException`

- Ja traduz os códigos erros do http
- Forma alternativa de lançamento de erros
- Colocamos essa anotação no controller
- Classe de exception do spring

```java
throw new ResponseStatusException(HttpStatus.NOT_FOUND,reason);
```

## Tratando exceções em nível de controlador com `@ExceptionHandler`

- Podemos customizar os erros ocorridos na aplicação no controller
- É possível customizar mensagens e códigos de erros
- Exemplo de utilização de um handler

```java
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException exception) {
        Problema problema = Problema.builder().setDataHora(LocalDateTime.now()).setMensagem(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
    }
}
```

- Podemos extender `ResponseEntityExceptionHandler` para tratar algumas exceções internas do spring
- Herdando da classe, evitamos tratar todos os erros da aplicação como por exemplo xml ou erros especificos do spring
- Eles não retornam um corpo na resposta do http. Porém é possível customizar
- Para customizar podemos sobrescrever o método `handleExceptionInternal`

```java
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Problema.builder().setDataHora(LocalDateTime.now()).setMensagem(status.getReasonPhrase());
        }
        if (body instanceof String) {
            body = Problema.builder().setDataHora(LocalDateTime.now()).setMensagem((String) body);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
```


## RFC 7807 e a especificação do retorno de erro de APIs

- Existe um documento que descreve uma possível boa prática para o retorno de erros de uma api
- Apresentar informações claras do erro para quem está consumindo o serviço da Api

![Captura de tela de 2022-08-10 19-28-47](https://user-images.githubusercontent.com/43495376/184031574-5ecf3884-dc75-491c-b000-97f611f9ba5e.png)

- Podemos extender o formato, incluindo propriedades específicos

![Captura de tela de 2022-08-10 19-33-11](https://user-images.githubusercontent.com/43495376/184032035-3653c28a-0211-4627-8e56-7b319d69cab8.png)

## Habilitando erros na desserialização de propriedades inexistentes ou ignoradas

- Podemos incluir tratamento de erros para propriedades inexistentes em um corpo de requisição
- Necessário adicionar nas propriedades para que seja possível lançar exceção caso uma propriedade inexistente seja incluida ou caso esteja faltando alguma propriedade necessária no corpo da requisição

```properties
spring.jackson.deserialization.fail-on-ignored-properties=true
spring.jackson.deserialization.fail-on-unknown-properties=true
```

- Para lançamento de erro também nos métodos de atualização de recursos, precisamos configurar o object mapper

```java
objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
```

- O tipo de exceção lançada é o `IlegalException`

## Tratando outras exceções não capturadas

- Geralmente esses tipos de erros é retornado com código 500
- Criamos um handler que captura `Exception.class`
- Exemplo de como podemos criar o tratamento

```java
@ExceptionHandler(Exception.class)
public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;		
    ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
    String detail = "Ocorreu um erro interno inesperado no sistema. "
            + "Tente novamente e se o problema persistir, entre em contato "
            + "com o administrador do sistema.";

    // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
    // fazendo logging) para mostrar a stacktrace no console
    // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
    // para você durante, especialmente na fase de desenvolvimento
    ex.printStackTrace();
    
    Problem problem = createProblemBuilder(status, problemType, detail).build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
} 
```

## Criando um banco de testes e usando `@TestPropertySource`

- Com essa propriedade ao execurar os testes usamos a propriedade que está na pasta resources da aplicação
- Uma alternativa de usar o mesmo tipo de banco de dados da aplicação ao invés de usar o h2
- Criamos uma propriedade `application-test.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/algafood_test
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.hikari.maximum-pool-size=1
```

- Anotamos a propriedade na classe de teste

```java
@TestPropertySource("application-test.properties")
```

## Limpando e populando banco de dados de teste

- Criamos uma classe que possui a implementação da limpeza dos dados no banco de testes
- A classe está no pacote util de teste `DatabaseCleaner`