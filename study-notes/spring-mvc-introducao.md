# Introdução ao Spring MVC

- Spring MVC é um dos populares frameworks web java baseado no design pattern MVC (Model, View e Controller)
- O gráfico abaixo mostra como funciona o fluxo de requisição e resposta no Spring MVC

![Captura de tela de 2023-02-08 18-41-42](https://user-images.githubusercontent.com/43495376/217657579-3a38d28b-a8da-42a2-997a-c3517d5c2e9f.png)

- O DispatcherServlet identificará o respectivo controlador baseado no mapeamento URL
- O ViewResolver retornará o HTML exibido no navegador web. O view é retornado pelo controlador


## Customizando views com Thymeleaf

### Usando variáveis dinâmicas

- No exemplo abaixo temos uma página HTML que realiza a iteração no posts (Definido no controller) e exibe cada elemento na página

```html
<div th:each="post : ${posts}">
    <h3 th:text="${post.title}"></h3>
    <h6 th:text="${post.description}"></h6>
    <div th:text="${post.body}"></div>
</div>
```

### Trabalhando com formulário

- Podemos renderizar o formulário. Exemplo de adição de posts

```html
<form th:object="${post}" th:action="@{/posts}" method="post">
  <div>
  <label for="title">Title:</label>
  <input id="title" th:field="${post.title}" type="text"/>
  </div>
 <div>
 <label for="description">Description:</label>
  <input id="description" th:field="${post.description}" type="text"/>
 </div>
  <div>
 <label for="body">Body:</label>
  <textarea id="body" th:field="${post.body}"></textarea>
  </div>
  <div>
  <input type="submit">
  </div>
</form>
```

### Implementando validação de formulário

- Spring possui um starter pra validações iniciais. Adicionamos a dependencia no projeto

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
```

- Exemplo de validação

```java
public class Post {

    private Integer id;

    @NotNull
    @Size(min = 3, max = 50, message = "Title must be minimum 3 characters, and maximum, 50 characters")
    private String title;

    @NotNull
    @Size(min = 3, max = 500, message = "Description must be minimum 3 characters, and maximum 500 characters")
    private String description;

    @NotNull
    @Size(min = 3, max = 5000, message = "Body must be minimum 3 characters, and maximum 5000 characters")
    private String body;

    private String slug;

    private PostStatus postStatus;

    private LocalDateTime createdOn;

    private List<Comment> comments;
}
```
