# Spring e injeção de dependências
## Porque utilizar Spring?
- Conjunto de projetos que resolve vários problemas no backend (Ecosistema de desenvolvimento do lado do servidor)
- Produtividade
- Maturidade
- Modularidade
- Evolução constante
- Open source

## Injeção de dependência
- Padrão de projeto que utiliza a injeção de controle com o objetivo de reduzir o forte acomplamento
- Facilita a substituição de funcionalidade
- Testes unitários com mais flexibilidade
- O spring gerencia as injeções através do Spring IoC Container

## Spring IoC Container
- Responsável por implementar as injeções de dependências
  ![Captura de tela de 2022-06-15 18-42-51](https://user-images.githubusercontent.com/43495376/173935004-e1cea892-fa6c-4d2d-a18f-54c44b3e1691.png)
- O bean é objeto instanciado pelo spring
- Podem ser injetados uns nos outros dependendo de suas necessidade

## Pontos de injeção de dependência do spring
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

## Resolvendo problemas de ambiguidade das injeções de dependências
- Podemos resolver o problema aceitando os múltiplos beans
```java
    @Autowired
    private List<Notificador> notificadores

    public ativar(Cliente cliente) {
        for(Notificador notificador: notificadores) {
            notificador.notificar(cliente, "Seu cadastro no sistema está ativo");
        }
    }
```
- Outra forma de resolver o problema, injetando apenas 1 notificador, usando a anotação `@Primary`
- Esta anotação define a prioridade no processo de injeção de dependência
```java
    @Primary 
    @Component 
    public class NotificadorSMS implements Notificador {
        @Autowired 
        private Notificador notificador;
    }
```
- Podemos desambiguar também usando anotação `@Qualifier`
- Identificação e qualificar o componente
```java
    @Qualifier("email")
    @Component 
    public class NotificadorEmail implements Notificador {
        @Autowired 
        private Notificador notificador;
    }

    @Qualifier("sms")
    @Component
    public class NotificadorSMS implements Notificador {
        @Autowired
        private Notificador notificador;
    }   
```

## Alterando comportamento da aplicação com o Spring Profiles
- A Aplicação pode ser adequar em diversos tipos de aplicação
- Geralmente utilizado para definir ambientes de desenvolvimento, testes e produção
- Podemos utilizar um componente de acordo com o profile da aplicação
```java
    @Profile("prod")
    @Component
    public class NotificadorEmail implements Notificador {
        @Autowired 
        private Notificador notificador;
    }
```
- No exemplo acima, esse componente só será executado se o profile for do tipo `prod`
- É definido atraves do arquivo o `application.properties`
```properties
    spring.profiles.active=prod
```
