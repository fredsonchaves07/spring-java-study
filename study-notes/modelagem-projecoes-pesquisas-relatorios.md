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

## Modelando endpoints de consultas com dados agregados (ideal para gr�ficos e dashboards)

- Criamos um servi�o agregado que retornara os dados com a consulta customizada
- Usamos essa estrat�gia ao inv�s de incluir o m�todo em um repos�t�rio de `Pedidos` por exemplo

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

- No controller temos a implementa��o

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

## Tratando time offset na agrega��o de vendas di�rias por data

- Podemos retornar os dados agrupados por data de acordo com o offset do usu�rio
- Evita problemas de inconsist�ncia de dados por data
- Exemplo de consulta com fun��o de convers�o de data

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

- O timeOffset � informado pelo controlador. Caso o usu�rio n�o informe, � assumido o valor padr�o de "00:00"

```java
    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(vendaDiariaFilter, timeOffset);
    }
```

## Estruturando endpoint e servi�o de emissao de relat�rios em PDF

- Podemos criar a estrutura do relat�rio atrav�s da ferramenta do JasperStudio
- Ap�s a cria��o do relat�rio, compilamos o arquivo e deixamos na pasta de relat�rios em resources
- Necess�rio adicionar a biblioteca do jasper reports

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
- Criamos um m�todo que aceitara o conteudo em pdf

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

- A implementa��o da classe de servi�o

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

## Validando o tamanho m�ximo do arquivo

- Incluimos as propriedades

```properties
spring.servlet.multipart.max-file-size=20KB
spring.servlet.multipart.max-request-size=20MB
```

- Caso o arquivo exceda o tamanho permitido, � lan�ado a exce��o `FileSizeLimitExceededException`
- Tamb�m � poss�vel definir limite para cada arquivo, caso temos uma aplica�� que aceita mais de 1 arquivo
- Criamos uma anota��o para que seja feito a valida��o

```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileSizeValidator.class})
@PositiveOrZero
public @interface FileSize {

    String message() default "Tamanho de arquivo inv�lido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String max();
}
```

- Implenta��o da anota��o

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

- Exemplo de utiliza��o da valida��o

```java
import com.fredsonchaves.algafood.core.validation.FileSize;

@FileSize(max = "500KB")
private MultipartFile arquivo;
```