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
protected ResponseEntity<Object> handleExceptionInternal(Exception ex,Object body,HttpHeaders headers,HttpStatus status,WebRequest request){
        if(body==null){
        body=Problema.builder().setDataHora(LocalDateTime.now()).setMensagem(status.getReasonPhrase());
        }
        if(body instanceof String){
        body=Problema.builder().setDataHora(LocalDateTime.now()).setMensagem((String)body);
        }
        return super.handleExceptionInternal(ex,body,headers,status,request);
        }
```