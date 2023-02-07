# Essenciais do Spring Boot

- O Principal objetivo do Spring Boot é tornar fácil o desenvolvimento de aplicações Spring
- Provê um conjunto de ferramentas como logging, externalização de configurações, entre outros que podem ser úteis no desenvolvimento

## Logging

- É essencial para qualquer aplicaçõe
- Ajuda a encontrar ajuda e reports de erros
- Spring por padrão inclui `spring-boot-starter-logging` como depdendencia transitiva para o módulo `spring-boot-starter`
- O SLF4j com implementação Logback 
- É possível configurar o nível de logging no arquivo `application.properties` sem a necessidade de criar arquivos como `logback.xml` ou `log4j.properties`

```properties
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=ERROR
logging.level.com.apress=DEBUG
```

- É possível armazenar as informações do log em arquivo informando a propriedade

```properties
logging.file.path=/var/logs/app.log
```

- Existem outras bibliotecas como Log4J ou Log4j2 que podem ser adicionadas no `pom.xml`. Sem a necessidade do `spring-boot-starter-logging`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j</artifactId>
</dependency>
```

## Externalizando as propriedades de configuração

- É importante externalizar as configurações da aplicação em arquivo depedendo do ambiente ao invés de deixar implicito no código
- O Spring possui a anotação `@PropertySource` para especificar a lista de arquivos de configuração
- Podemos criar arquivos `.properties` para cada tipo de ambiente da aplicação (Desenvolvimento, testes e produção)

```properties
application-{profile}.properties
```

### Propriedades de configuração de tipo seguro

- Spring provê a anotação `@Value` para definir o valor da propriedade

```java
@Configuration
public class AppConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    ...
    ...
}
```
- Para cada tipo de configuração anotar com o `@Value` pode ser custoso, mas Spring Boot provê formas automaticas de definição de configuração com modo seguro

### Validação de propriedades com API Bean Validation

- Para validar propriedades podemos usar algumas anotações como `@NotNull`, `@Min`, `@Max` entre outros

```java
@Component
@ConfigurationProperties(prefix="support")
public class Support {
    @NotNull
    private String applicationName;
    @NotNull
    @Email
    private String email;
    @Min(1) @Max(5)
    private Integer severity;
    //setters and getters
}
```
- Para habilitar a validação é necessário incluir a dependencia no arquivo `pom.xml`

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

## Ferramentas de Desenvolvimento

- Durante o desenvolvimento, pode ser necessário alterar o código e reiniciar o servidor para aplicar as alterações
- Spring Boot prové um conjunto de ferramentas de desenvolvimento `spring-boot-devtools`
- Esta depedencia provê a renicialização rápida após as alterações no código sem necessidade de reinicir o servidor

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```
