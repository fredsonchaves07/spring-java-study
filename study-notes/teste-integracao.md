# Testes de integração com Spring

## Preparando projeto para rodar os testes

- Ao instalar o spring no projeto por padrão já vem com a biblioteca JUnit instalado

```properties
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```

- Os testes podem ser executados na própria IDE ou executar o comando abaixo

```shell
mvn test
```

## Configurando Maven Failsafe plugin no projeto

- Plugin do maven criado para execução de testes de integração
- A execução de testes de integração são demorados
- Uma boa alternativa é ignorar esses testes no processo de compilação do projeto
- Adicionamos um plugin no arquivo `pom.xml`

```properties
            <plugin>
                <artifactId>maven-failsafe-plugin</artifactId>
            </plugin>
```

- É necessário alterar o nome da classe de teste para que não seja executado. Acrescentamos o sufixo `IT`
- Para executar os testes de integração usamos o comando `mvn verify`

## Implementando testes de Api com REST Assured e validando o código de status HTTP

- Podemos usar a biblioteca Rest assured para validar o status code e conteúdo das requisições http

```properties
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <scope>test</scope>
        </dependency>
```

- Exemplo de como podemos utilizar o teste

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIntegrationIT {

    @LocalServerPort
    private int port;

    @Test
    public void deveRetornarStatus200QuandoConsultarCozinhas() {
        RestAssured.given()
                .basePath("/cozinhas")
                .port(8080)
                .accept(ContentType.JSON)
                .when().get()
                .then().statusCode(HttpStatus.OK.value());
    }
}
```

## Criando métodos de preparação de testes

- Uma boa prática é criar um método para setar dados ou configurações a ser utilizados a cada testes
- Pode ser usado para evitar duplicação de códigos

```java
    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
    }
```


## Criando um banco de testes e usando `@TestPropertySource`

- Com essa propriedade ao execurar os testes usamos a propriedade que está na pasta resources da aplicação
- Uma alternativa de usar o mesmo tipo de banco de dados da aplicação ao invés de usar o h2
- Criamos uma propriedade `application-test.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/algafood_test
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.hikari.maximum-pool-size=1
```

- Anotamos a propriedade na classe de teste

```java
@TestPropertySource("application-test.properties")
```

## Limpando e populando banco de dados de teste

- Criamos uma classe que possui a implementação da limpeza dos dados no banco de testes
- A classe está no pacote util de teste `DatabaseCleaner`