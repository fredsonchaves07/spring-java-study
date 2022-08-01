# Pool de conexões e Flyway

## O que é um pool de conexões?
- É um componente de software que pode ser utilizado para manter um conjunto de conexões ou banco de dados com o objetivo de reutilização do banco de dados
- Uma mesma conexão é usada diversas vezes em momentos diferentes

## Aplicação sem pool de conexões

- Cria uma nova conexão toda vez que ocorrer uma interação entre o banco de dados
- A conexão entre o banco de dados ocorre a medida que ocorre a requisição
  ![Captura de tela de 2022-08-01 11-50-06](https://user-images.githubusercontent.com/43495376/182177063-7a7213a0-f1b1-45b0-8b2e-02741ff1a7da.png)

## Aplicação com pool de conexões

- Aplicação prepara a conexão com o banco de dados
- A conexão com o banco de dados pode ser um processo custoso pois em alguns casos a conexão com o banco de dados pode
  demorar
  ![Captura de tela de 2022-08-01 12-06-41](https://user-images.githubusercontent.com/43495376/182180563-7b59ca16-66f2-4f9b-9f82-78fde3091bfa.png)

## Criando e configurando um pool de conexão

- Por padrão, o Spring cria 10 conexões
- O Spring data JPA possui a dependencia hikari responsavel por criar e gerenciar o pool de conexoes
```properties
spring.datasource.hikari.maximum-pool-size=5
spring.datasource.hikari.minimum-idle=3
spring.datasource.hikari.idle-timeout=10000
```