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
- ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <entity-mappings
    xmlns="http://xmlns.jcp.org/xml/ns/persistence/orm"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/orm_2_2.xsd"
    version="2.2">
    <named-query name="Restaurante.consultaPorNome">
    <query>
    from Restaurante
    where nome like concat('%', :nome%, '%')
    and cozinha.id = :id
    </query>
    </named-query>
    </entity-mappings>

```