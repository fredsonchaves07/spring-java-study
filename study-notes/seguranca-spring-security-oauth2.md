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

### Fluxo para emitir e usar Refresh Tokens

- Uma alternativa para regerar tokens inválidos (troca um access token que foi expirado)
- Resolve o problema de ter que ficar interagindo com usuário para solicitar usuario e senha

![Captura de tela de 2022-12-21 11-33-58](https://user-images.githubusercontent.com/43495376/208929772-01dce512-e0de-4575-95a6-6117fdacccdb.png)

### Fluxo Client Credentials

- Permite obter um access token usando apenas as credenciais do client
- Fluxo que não tem interação com usuário final

![Captura de tela de 2022-12-22 11-11-32](https://user-images.githubusercontent.com/43495376/209152607-e323665b-3192-427e-9c4f-3b04576e1fb5.png)

## Qual fluxo do OAuth2 usar?

![Captura de tela de 2023-01-10 15-07-48](https://user-images.githubusercontent.com/43495376/211628862-c58db530-fad2-472f-b70f-afaffdc425c4.png)
