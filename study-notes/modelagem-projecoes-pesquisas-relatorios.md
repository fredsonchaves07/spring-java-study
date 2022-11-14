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
        var functionDateDataCriacao = builder.function("date", Date.class, root.get("dataCriacao"));
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

## Tratando time offset na agregação de vendas diárias por data

- Podemos retornar os dados agrupados por data de acordo com o offset do usuário
- Evita problemas de inconsistência de dados por data
- Exemplo de consulta com função de conversão de data

```java
        var functionConvertTzDataCriacao = builder.function(
                "convert_tz",
                Date.class,
                root.get("dataCriacao"),
                builder.literal("+00:00"),
                builder.literal(timeOffset)
        );
        var functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);
```

- O timeOffset é informado pelo controlador. Caso o usuário não informe, é assumido o valor padrão de "00:00"

```java
    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(vendaDiariaFilter, timeOffset);
    }
```

## Estruturando endpoint e serviço de emissao de relatórios em PDF

- Podemos criar a estrutura do relatório através da ferramenta do JasperStudio
- Após a criação do relatório, compilamos o arquivo e deixamos na pasta de relatórios em resources
- Necessário adicionar a biblioteca do jasper reports

```xml
        <properties>
            <java.version>17</java.version>
            <jasperreports.version>6.20.0</jasperreports.version>
        </properties>
        <dependency>
            <groupId>net.sf.jasperreports</groupId>
            <artifactId>jasperreports</artifactId>
            <version>${jasperreports.version}</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jasperreports</groupId>
            <artifactId>jasperreports-functions</artifactId>
            <version>${jasperreports.version}</version>
        </dependency>
```
- Criamos um método que aceitara o conteudo em pdf

```java
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter vendaDiariaFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) throws JRException {
        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(vendaDiariaFilter, timeOffset);
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diaria.pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }
```

- A implementação da classe de serviço

```java
    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        try {
            var inputStream = this.getClass().getResourceAsStream("/reports/vendas-diarias.jasper");
            var parametros = new HashMap<String, Object>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
            var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
            var datasource = new JRBeanCollectionDataSource(vendasDiarias);
            var jasperPrint = JasperFillManager.fillReport(inputStream, parametros, datasource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Teste");
        }
    }
```

## Validando o tamanho máximo do arquivo

- Incluimos as propriedades

```properties
spring.servlet.multipart.max-file-size=20KB
spring.servlet.multipart.max-request-size=20MB
```

- Caso o arquivo exceda o tamanho permitido, é lançado a exceção `FileSizeLimitExceededException`
- Também é possível definir limite para cada arquivo, caso temos uma aplicaçã que aceita mais de 1 arquivo
- Criamos uma anotação para que seja feito a validação

```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileSizeValidator.class})
@PositiveOrZero
public @interface FileSize {

    String message() default "Tamanho de arquivo inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String max();
}
```

- Implentação da anotação

```java
public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize maxSize;

    @Override
    public void initialize(FileSize fileSize) {
        this.maxSize = DataSize.parse(fileSize.max());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return file == null || file.getSize() <= this.maxSize.toBytes();
    }
}
```

- Exemplo de utilização da validação

```java
import com.fredsonchaves.algafood.core.validation.FileSize;

@FileSize(max = "500KB")
private MultipartFile arquivo;
```