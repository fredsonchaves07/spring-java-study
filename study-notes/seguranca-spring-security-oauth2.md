# Segurança com Spring Security e OAuth2

- Para a implementação dos recursos de segurança. É necessário adicionar a dependência no arquivo `pom.xml`

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

## Introdução ao OAuth2

- É um framework de autorização que permite aplicações terceiras acessem recursos limitados
- Uma especificação, um protocolo padrão
- Define 4 papeis envolvidos na comunicação 