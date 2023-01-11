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
