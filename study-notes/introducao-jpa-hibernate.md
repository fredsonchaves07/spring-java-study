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
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>
```

- Necessário realizar a configuração do datasource no arquivo `application.properties`

```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/algafood
  spring.datasource.username=postgres
  spring.datasource.password=1234
  spring.jpa.hibernate.ddl-auto=create
```
