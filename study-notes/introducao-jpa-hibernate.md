# Introdução ao JPA e Hibernate

## O que é persistência de dados?
- Preservação dos dados por muito tempo
- Persistidos, gravados em algum local (banco de dados)
![Captura de tela de 2022-06-24 15-50-49](https://user-images.githubusercontent.com/43495376/175647524-31ca592f-34d1-41d1-9196-be985dea9dd9.png)
- O Drive JDBC é um componente de sofrware que faz a comunicação entre a aplicação e o banco de dados

## ORM (Object Relational Mapping)
- Técnica que realiza o mapeamento entre classes da orientação a objetos para tabelas do banco de dados
- Mais orientação objetos e menos modelo relacional
![Captura de tela de 2022-06-24 15-57-25](https://user-images.githubusercontent.com/43495376/175648100-0fbedcb5-08a0-4c34-b2d9-7a659b955c17.png)
- Exemplo da relação da tabela com a classe
![Captura de tela de 2022-06-24 16-00-48](https://user-images.githubusercontent.com/43495376/175648562-8a0761d2-08cd-4bac-8015-3345d9d00479.png)
- Com a utilização do ORM, o código java realiza a comunicação entre a solução que usa ORM

## Java Persistence API (JPA)
- Especificação da Java EE e define como deve ser realizado o mapeamento de objeto relacional;
- Descreve regras específicas de conexão com o banco de dados
![Captura de tela de 2022-06-24 16-08-51](https://user-images.githubusercontent.com/43495376/175649695-d6ac1495-6df7-4a0a-a8e2-76ed58d3b9f0.png)


## Hibernate
- É uma implementação da JPA (Produto)

## Configurando aplicação para utilização do JPA e Hibernate
- O Spring Data JPA possui um conjunto de ferramentas que utilizam JPA e Hibernate

```java
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
        </dependency>
```

- Necessário realizar a configuração do datasource no arquivo `application.properties`

```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/algafood
  spring.datasource.username=postgres
  spring.datasource.password=1234
  spring.jpa.hibernate.ddl-auto=create
```
## Mapeando os objetos
### `@Entity`
- Usamos a anotação `@Entity` para realizar o mapeamento das entidades

```java
@Entity
public class Restaurante {

    @Id
    public Long id;

    private String nome;

    @Column(name = "taxa_frete")
    private BigDecimal taxaFrete;
}
```

### `@Id`
- Define o campo a ser utilizado como chave primária da tabela
- Essa anotação é obrigatória para definição das entidades

```java
    @Id
    public Long id;
```

- Nesse atributo, usamos a anotação `@GeneratedValue` para definir a estratégia de geração auto increment no banco
- A estratégia mais comum é `GenerationType.IDENTITY` que repassa ao banco de dados a forma de como deve ser realizado o auto increment

```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
```

### `@Table` e `@Column`
- Anotações utilizadas para definir o nome da tabela e coluna

```java
    @Entity
    @Table(name = "tab_cozinhas")
    public class Cozinha {

        @Column(name = "nom_cozinha")
        private String nome;
    }
```

## Importando dados automaticamente para as tabelas
- Podemos realizar a importação dos valores no arquivo `import.sql`
- Ao iniciar a aplicação, o spring executará os scripts que estão neste arquivo


## Estados de uma entidade
- Uma entidade pode assumir alguns estados que podem ser:
    - Novo (new ou transient) -> Quando um construmos um objeto novo
    - Gerenciado (managed) -> Podemos chamar os métodos `persist` e `merge` para buscar uma entidade
    - Removido (removed) -> Ocorre quando chamaos o método `remove`
    - Desanexado (detached)  -> Entidade fica no estado "Desanexado" passada para o método `detach`
![Diagrama-de-estados](https://user-images.githubusercontent.com/43495376/176482845-e8ab64ff-4d46-46e9-bdad-36dab4e0cd5d.png)

## Mapeamento de entidades
- Usamos essas anotações para relacionar entidades

### Relacionamento N para 1
- Faz o relacionamento de muitos para 1 no banco de dados

```java
    @ManyToOne
    private Cozinha cozinha;
```

- Podemos alterar o nome da chave estrangeira usando esta anotação

```java
    @JoinColumn(name = "cozinha_id")
```