# Spring Data JPA

## Linguagem de consultas da JPA

- JPQL é a linguagem de consultas utilizada pela JPA
- Possui sintaxe próxima ao SQL
- Exemplo de utilização da JPQL

```java
    @Override
public List<Cozinha> consultarPorNome(String nome){
        return entityManager.createQuery("from Cozinha where nome = :nome",Cozinha.class)
        .setParameter("nome",nome)
        .getResultList();
        }
```

## O que é Spring Data JPA?

- Fornece um repositório genérico. É um sub projeto do Spring Data
- Eliminação do código boilerplate
- Instancia em tempo de execução os métodos da JPA
- Exemplo de um repositório utilizando o Spring Data JPA

```java
    public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
```

## Criando queries JPQL customizadas

- Usamos a anotação `@Query` nos métodos para criar consultas customizadas

```java
    @Query("from Restaurante where nome like %:nome%")
    List<Cozinha> consultarPorNome(String nome);
```

## Externalizando consultas JPQL para um arquivo XML

- Uma alternativa ao `@Query` é externalizar consultas por meio de um arquivo XML
- Separa as consultas SQL da aplicação
- Criamos um arquivo `orm.xml` na pasta `META_INF` no resources
```xml
    <?xml version="1.0" encoding="UTF-8"?>
<entity-mappings
        xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/orm_2_2.xsd"
        version="2.2">
    <named-query name="Restaurante.consultaPorNome">
        <query>
            from Restaurante
            where nome like concat('%', :nome, '%')
            and cozinha.id = :id
        </query>
    </named-query>
</entity-mappings>
```

## Implementando repositório customizados

- Podemos criar classes que implementam os repositórios do Spring Data JPA
- Pode ser útil quando temos consultas mais complexas ou quando queremos incluir códigos Java
- Para criar implementação cuztomizada é necessário ter o sufixo `Imp` no nome da classe

```java
import com.fredsonchaves.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaInicial and :taxaFinal";
        return entityManager.createQuery(jpql, Restaurante.class)
                .setParameter("nome", "%" + nome + "%")
                .setParameter("taxaInicial", taxaFreteInicial)
                .setParameter("taxaFinal", taxaFreteFinal)
                .getResultList();
    }
}
```

- Para usarmos, basta realizar o xtends do `RestauranteRepositoryQuery` no `RestauranteRepository`

## Implementando consultas dinamicas

- Em alguns casos pode ser útil criamos algumas consultas dinamicos com busca e retorno de dados mais complexos
- Exemplo de utilização de uma consulta dinamica onde o valor é retornado de acordo com os parâmetros informados

```java
    @Override
public List<Restaurante> find(String nome,BigDecimal taxaFreteInicial,BigDecimal taxaFreteFinal){
        var jpql=new StringBuilder();
        var params=new HashMap<String, Object>();
        jpql.append("from Restaurante where 0 = 0 ");
        if(StringUtils.hasLength(nome)){
        jpql.append("and nome like :nome ");
        params.put("nome","%"+nome+"%");
        }
        if(taxaFreteInicial!=null){
        jpql.append("and taxaFrete >= :taxaInicial ");
        params.put("taxaInicial",taxaFreteInicial);
        }
        if(taxaFreteFinal!=null){
        jpql.append("and taxaFrete <= :taxaFinal ");
        params.put("taxaFinal",taxaFreteFinal);
        }
        TypedQuery<Restaurante> query=entityManager
        .createQuery(jpql.toString(),Restaurante.class);

        params.forEach(query::setParameter);
        return query.getResultList();
        }
```

## Criteria API

- API da Jpa para criação de querys programáticas
- Útil para criação de querys complexas e dinâmicas
- Utilização de código JAVA na criação de consultas SQL
- Exemplo de consulta simples com criteria API

```java

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
        criteriaQuery.from(Restaurante.class);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
```

- Criando uma consulta dinâmica com Criteria API

```java
    @Override
public List<Restaurante> find(String nome,BigDecimal taxaFreteInicial,BigDecimal taxaFreteFinal){
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteriaQuery=criteriaBuilder.createQuery(Restaurante.class);
        var predicates=new ArrayList<Predicate>();
        Root<Restaurante> root=criteriaQuery.from(Restaurante.class);
        if(StringUtils.hasText(nome)){
        predicates.add(criteriaBuilder.like(root.get("nome"),"%"+nome+"%"));
        }
        if(taxaFreteInicial!=null){
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"),taxaFreteInicial));
        }
        if(taxaFreteFinal!=null){
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"),taxaFreteFinal));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
        }
```
