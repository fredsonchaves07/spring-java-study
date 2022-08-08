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