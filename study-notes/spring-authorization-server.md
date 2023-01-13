# Spring Authorization Server

- Nova solução do ecosistema Spring
- Provê um servidor de autenticação seguindo a especificação OAuth

![Captura de tela de 2023-01-13 11-52-16](https://user-images.githubusercontent.com/43495376/212349486-74d102e3-03ea-44d9-b9d9-b6a716793481.png)


## Configurando com novo Spring Authorization Server

- Criamos uma classe que armazenará a propriedade que contém a URL do servidor de autenticação

```java
  @Component
  @ConfigurationProperties("moviecatch.auth")
  public class MovieCatchSecurityProperties {

      private String providerUrl;

      public String getProviderUrl() {
          return providerUrl;
      }

      public void setProviderUrl(String providerUrl) {
          this.providerUrl = providerUrl;
      }
  }
```

- Criamos a classe de configuração de `AuthorizationServerConfig` que está utilizando o exemplo do fluxo opacoToken

```java
@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception{
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    @Bean
    public ProviderSettings providerSettings(MovieCatchSecurityProperties properties) {
        return ProviderSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient moviecatchbackend = RegisteredClient
                .withId("1")
                .clientId("moviecatch-backend")
                .clientSecret(passwordEncoder.encode("movie@123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();
        return new InMemoryRegisteredClientRepository(Collections.singletonList(moviecatchbackend));
    }
}
```

- Configuramos o Postman para testar a requisição usando o fluxo OPACO

![Captura de tela de 2023-01-13 13-06-38](https://user-images.githubusercontent.com/43495376/212365761-69bd4482-d9b5-47a1-9557-1c4086233317.png)

![Captura de tela de 2023-01-13 13-06-28](https://user-images.githubusercontent.com/43495376/212365749-b3ac365a-a130-420e-b6c0-e49a35b1279f.png)


