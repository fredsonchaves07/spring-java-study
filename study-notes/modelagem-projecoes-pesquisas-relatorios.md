# Modelagem de projeções, pesquisas e relatórios

## Limitando os campos retornados pela API com Squiggly

- Uma biblioteca que ajuda a limitar as propriedades do recurso
- Possui uma documentação e bem fácil sua utilização https://github.com/bohnman/squiggly
- Os filtros são usados nos parâmetros da URL de forma única. É retornado as informações de acordo com os valores informados na propriedade `fields` da requisição
- Criamos uma classe de configuração no pacote `squiggly`

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

## Tratando `BindException` ao enviar parâmetros de URL inválidos

- Esse tipo de erro ocorre quando passamos um valor inválido de um parâmetro no recurso
- Podemos criar um handler para tratar esse tipo de erro.
- Informamos a exception `BindException`


## Paginação e ordenação de recursos

- A ideia da paginação e ordenação serve para otimizar o retorno das informações dos recursos
- O retorno dos recursos é realizado de acordo com os parâmetros informados na URL, de qual página o usuário quer obter
- Usamos os parâmetros `size`, `page` para filtrar os recursos
- Exemplo de paginação de recurso

```java
    @GetMapping()
    public List<Cozinha> listas(Pageable pageable) {
        Page<Cozinha> pageCozinha =  cozinhaRepository.findAll(pageable);
        return pageCozinha.getContent();
    }
```

- A propriedade `sort` define qual valor o recurso deve ser ordenado. Se é por nome, data, valor. etc
- Por padrão a ordenação é ascendente.
- Caso, o usuario queira ordenar os recursos em ordem decrescente. Passamos o valor desc na propriedade
- Podemos incluir as informações da paginação. Nesse caso, alteramos o retorno do controller para `Page<T>`
- A implementação do page: `new PageImpl<>(listResource, pageable, page.getTotalElements()`

## Implementando JsonSerializer para customizar representação de paginação

- Usamos esse recurso para retirar algumas informações não necessárias da paginação do spring
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

## Implementando um conversor de propriedades de ordenação

- Por padrão a ordenação das propriedades é realizada de acordo com a entidade
- Gera o problema de expor a entidade
- Uma das estratégia para evitar esse tipo de problema é realizar uma tradução de propriedades (usuário informa uma propriedade que é diferente da entidade)
- Exemplo de tradução de um novo Pageable

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

## Modelando endpoints de consultas com dados agregados (ideal para gráficos e dashboards)

- Criamos um serviço agregado que retornara os dados com a consulta customizada
- Usamos essa estratégia ao invés de incluir o método em um reposítório de `Pedidos` por exemplo

```java
Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter) {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Restaurante.class);
        var functionDateDataCriacao = builder.function("date", LocalDate.class, root.get("dataCriacao"));
        var selection = builder.construct(VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));
        query.select(selection);
        query.groupBy(functionDateDataCriacao);
        return entityManager.createQuery(query).getResultList();
    }
}
```

- No controller temos a implementação

```java
@RestController
@RequestMapping(path = "/estatiticas")
public class EstatiticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter) {
        return vendaQueryService.consultarVendasDiarias(vendaDiariaFilter);
    }
}
```