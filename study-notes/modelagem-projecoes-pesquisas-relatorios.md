# Modelagem de proje��es, pesquisas e relat�rios

## Limitando os campos retornados pela API com Squiggly

- Uma biblioteca que ajuda a limitar as propriedades do recurso
- Possui uma documenta��o e bem f�cil sua utiliza��o https://github.com/bohnman/squiggly
- Os filtros s�o usados nos par�metros da URL de forma �nica. � retornado as informa��es de acordo com os valores informados na propriedade `fields` da requisi��o
- Criamos uma classe de configura��o no pacote `squiggly`

```java
@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider());
        var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setOrder(1);
        return filterRegistration;
    }
}
```

## Tratando `BindException` ao enviar par�metros de URL inv�lidos

- Esse tipo de erro ocorre quando passamos um valor inv�lido de um par�metro no recurso
- Podemos criar um handler para tratar esse tipo de erro.
- Informamos a exception `BindException`


## Pagina��o e ordena��o de recursos

- A ideia da pagina��o e ordena��o serve para otimizar o retorno das informa��es dos recursos
- O retorno dos recursos � realizado de acordo com os par�metros informados na URL, de qual p�gina o usu�rio quer obter
- Usamos os par�metros `size`, `page` para filtrar os recursos
- Exemplo de pagina��o de recurso

```java
    @GetMapping()
    public List<Cozinha> listas(Pageable pageable) {
        Page<Cozinha> pageCozinha =  cozinhaRepository.findAll(pageable);
        return pageCozinha.getContent();
    }
```

- A propriedade `sort` define qual valor o recurso deve ser ordenado. Se � por nome, data, valor. etc
- Por padr�o a ordena��o � ascendente.
- Caso, o usuario queira ordenar os recursos em ordem decrescente. Passamos o valor desc na propriedade
- Podemos incluir as informa��es da pagina��o. Nesse caso, alteramos o retorno do controller para `Page<T>`
- A implementa��o do page: `new PageImpl<>(listResource, pageable, page.getTotalElements()`

## Implementando JsonSerializer para customizar representa��o de pagina��o

- Usamos esse recurso para retirar algumas informa��es n�o necess�rias da pagina��o do spring
- Exemplo de serializador

```java
@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {


    @Override
    public void serialize(Page<?> page,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("content", page.getContent());
        jsonGenerator.writeNumberField("size", page.getSize());
        jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
        jsonGenerator.writeNumberField("totalPages", page.getTotalPages());
        jsonGenerator.writeNumberField("number", page.getNumber());
        jsonGenerator.writeEndObject();
    }
}
```

## Implementando um conversor de propriedades de ordena��o

- Por padr�o a ordena��o das propriedades � realizada de acordo com a entidade
- Gera o problema de expor a entidade
- Uma das estrat�gia para evitar esse tipo de problema � realizar uma tradu��o de propriedades (usu�rio informa uma propriedade que � diferente da entidade)
- Exemplo de tradu��o de um novo Pageable

```java
  private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of(
                "nomeCliente", "cliente.nome",
                "codigo", "codigo",
                "restauranteNome", "restaurante.nome",
                "valorToral", "valorTotal"
        );
        var orders =  apiPageable.getSort().stream()
                .filter(order -> mapeamento.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(),
                        mapeamento.get(order.getProperty())))
                .collect(Collectors.toList());
        return PageRequest.of(apiPageable.getPageNumber(), apiPageable.getPageSize(), Sort.by(orders));
    }
```

