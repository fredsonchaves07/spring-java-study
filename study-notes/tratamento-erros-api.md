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