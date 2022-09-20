# Boas práticas no desenvolvimento de Api com Spring

## Criando classes de mixin para usar as anotações do Jackson

- Podemos criar classes de configuração que usam anotações do jackson para separar com a classe de entidade do dominio
- Útil quando temos muitas anotações nas classes de dominios
- Separação de responsabilidades
- Exemplo de criação de classe de mixin

```java
public class RestauranteMixin {

    @JsonIgnore
    public List<Produto> produtos = new ArrayList<>();

    @JsonIgnore
    private List<FormaPagamento> formaPagamentos = new ArrayList<>();

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;
}
```

- Linkamos as duas classes através do `setMixInAnnotation`

```java
public class JacksonMixinModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }
}
```

## Trabalhando com datas e horas

- Usar ISO-8601 para formatação de data/hora
- É um padrão flexível que existe várias combinações possíveis
- Exemplo de utilização, com representação do offset UTC (-03h de UTC -> Horário de Brasilia)

```json
{
  "lastLoginDate": "2019-10-12T14:15:38-03:00"
}
```

- Padrão com offset UTC global
- Para encontrar o horário de brasilia basta diminuir -03h

```json
{
  "lastLoginDate": "2019-10-12T14:15:38z"
}
```

- Api deve aceitar qualquer fuso de horário e converter para um fuso horario que api esteja utilizando
- Deve armazenar e retornar em UTC
- Não inclua o horário, se não for necessário
- Para alterar no código a data hora em formato UTC no sistema

```java
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;
```
- Seta a hora de acordo com o timeZone que chama o backend

```java
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(AlgafoodApplication.class, args);
    }
```