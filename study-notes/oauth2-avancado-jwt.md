# OAuth2 avançado com JWT e controle de acesso

## Diferença entre Stateful e Stateless Authentication

- Não podemos armazenar o estado da informação no servidor
- O servidor não precisa consultar informações contextuais da requisição

![Captura de tela de 2023-01-11 14-42-59](https://user-images.githubusercontent.com/43495376/211879303-51708324-ef13-4280-a539-dd9114c11cab.png)

- A principal desvantagem de utilizar essa abordagem é necessidade de infraestrutura no Authorization Server para armazenar o token
- Cria uma dependencia entre todos os resources servers

![Captura de tela de 2023-01-11 14-52-37](https://user-images.githubusercontent.com/43495376/211881231-0297ca0f-fd2b-471b-b617-7ab238cc3a3c.png)

- O Token possui informações transparênte
- Não possui nenhum mecanismo que armazena o token
- Mais dados trafegados na requisição

### Qual solução escolher?

- Não existe uma resposta para todos os casos
- Se precisar revogar tokens talvez seja interessante utilziar Statefull
- Se tiver muitos serviços e recursos utilizando a aplicação, talvez seja interessante utilizar stateless


## Json Web Token (JWT)

- Uma string que contém informações de acesso e autorização

## Assinatura com chave simétrica

- Compartilham a mesma chave. O emissor deve ser confiável

![Captura de tela de 2023-01-12 11-48-39](https://user-images.githubusercontent.com/43495376/212097954-3fbad675-0b1a-4ee2-9f50-d6a43ed02499.png)

## Assinatura com chave assimétrica

- Utiliza um conjunto de chave pública e privada
- A chave privada é usada para assinar o jwt
- A chave publica é utilizada pelo receptor para validação da assinatura do token

![Captura de tela de 2023-01-12 11-50-02](https://user-images.githubusercontent.com/43495376/212098255-e0f1fa04-1db3-4059-9ba4-f5f2ef6fd705.png)

- Para gerar a chave podemos executar o comando como abaixo

```shell
keytool -genkeypair -alias moviecatchapi -keyalg RSA -keypass 85de3caf-afe6-41d5-8144-573f916ffe42 -keystore moviecatch.jks -storepass movie@123 
```

- Para listar as chaves usamos o comando abaixo informando o storepass

```shell
keytool -list -keystore moviecatch.jks
```

- Exportamos um arquivo que conterá a chave pública

```shell
keytool -export -rfc -alias moviecatchapi -keystore moviecatch.jks -file moviecatch-cert.pem
```
