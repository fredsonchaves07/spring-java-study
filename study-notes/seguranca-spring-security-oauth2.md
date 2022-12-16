# Seguran�a com Spring Security e OAuth2

- Para a implementa��o dos recursos de seguran�a. � necess�rio adicionar a depend�ncia no arquivo `pom.xml`

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
        </dependency>
```

## Introdu��o ao OAuth2

- � um framework de autoriza��o que permite aplica��es terceiras acessem recursos limitados
- Uma especifica��o, um protocolo padr�o
- Define 4 papeis envolvidos na comunica��o 