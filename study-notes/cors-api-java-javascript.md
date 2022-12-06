# CORS e consumo da API com Javascript e Java

## O que � CORS?

- O CORS � um recurso de seguran�a aplicado nos navegadores que evita as requisi��es cruzadas
- Necess�rio definir permiss�es para origem cruzadas dos navegadores. Isso � realizado nos cabeca�hos HTTP

## Habilitando CORS no controlador

- Usamos a anota��o `@CrossOrigin` informando o valor do client que ser� liberado
- Por padr�o, o spring retorna o codstatus 403 caso a origem n�o esteja nas regras de libera��o
- Caso seja necess�rio liberar todas as origens usamos o valor `*`
- Essa anota��o pode ser usada nos m�todos como tamb�m nos controladores

## Habilitando CORS globalmente na api

- Exemplo de classe que implementa a configura��o do CORS globalmente

```java
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Permite todas as origens
//                .allowedOrigins("*")
                // Exemplo de permiss�o de alguns m�todos
                .allowedMethods("GET", "POST")
                // Cache ddo Option
                .maxAge(30);
    }
}
```