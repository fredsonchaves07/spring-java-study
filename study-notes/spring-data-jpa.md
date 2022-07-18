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