# spring-java-study
Repositório de estudos do framework Spring

## Introdução

### O que é uma API?
- Uma interface que fornece contratos e estruturas entre o consumidor e o produtor
- Exemplos de utilização de API
![Captura de tela de 2022-06-10 12-43-04](https://user-images.githubusercontent.com/43495376/173102215-fbeacbcf-4720-47c9-90b9-e06d4f52136d.png)

### Webservices x APIs
- Uma API que fornece serviços na internet (Comunicação é realizada na Web)
- Toda webservice é uma API mas nem toda API é um websevice

## Spring e injeção de dependências
### Porque utilizar Spring?
- Conjunto de projetos que resolve vários problemas no backend (Ecosistema de desenvolvimento do lado do servidor)
- Produtividade
- Maturidade
- Modularidade
- Evolução constante
- Open source

### Injeção de dependência
- Padrão de projeto que utiliza a injeção de controle com o objetivo de reduzir o forte acomplamento
- Facilita a substituição de funcionalidade
- Testes unitários com mais flexibilidade
- O spring gerencia as injeções através do Spring IoC Container

### Spring IoC Container
- Responsável por implementar as injeções de dependências
![Captura de tela de 2022-06-15 18-42-51](https://user-images.githubusercontent.com/43495376/173935004-e1cea892-fa6c-4d2d-a18f-54c44b3e1691.png)
- O bean é objeto instanciado pelo spring
- Podem ser injetados uns nos outros dependendo de suas necessidade

### Pontos de injeção de dependência do spring
- Onde podemos realizar a instanciação dos objetos nos beans
- A anotação `@Autowired` pode ser usada para fazer a instanciação
- Podemos usar essa anotação no construtor

```java

    @Autowired
    public AtivacaoClienteService(Notificador notificador) {
        this.notificador = notificador;
   }
```
- Também é possível realizar a instanciação através do método `set`
```java

    @Autowired
    public void setNotificador(Notificador notificador) {
        this.notificador = notificador;
   }
```
- Outro ponto de injeção é no atributo
```java

    @Autowired
    private Notificador notificador;
```
- O ideal é utilizar o `@Autowired` no construtor, porém o mais utilizado é no atributo