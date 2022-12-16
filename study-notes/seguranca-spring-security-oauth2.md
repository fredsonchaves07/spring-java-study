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

![Captura de tela de 2022-12-16 10-43-01](https://user-images.githubusercontent.com/43495376/208111545-d0d90ca6-bb84-4685-ab65-de4fd9cc4942.png)

- Um fluxo básico de como funcionar os papeis envolvidos na comunicação

![Captura de tela de 2022-12-16 10-51-06](https://user-images.githubusercontent.com/43495376/208112860-6a94fd2b-423b-4f36-816b-f6cc3eb0f558.png)

### Fluxo Resource Owner Password Credentials

- É a sequência de comunicação que ocorre entre os componentes
- Uma forma de obter um access token a partir de um usuário e senha
- O Cliente deve ser uma aplicação confiável (não recomendável por terceiros)
- Ideal para clients desenvolvidos por você mesmo
- Um fluxo não muito recomendável caso exista um outro que resolva um problema

![Captura de tela de 2022-12-16 11-10-16](https://user-images.githubusercontent.com/43495376/208116581-a817bfa0-77c7-4bc5-8050-fcb38d4ce020.png)

