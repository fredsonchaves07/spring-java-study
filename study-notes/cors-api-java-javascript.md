# CORS e consumo da API com Javascript e Java

## O que é CORS?

- O CORS é um recurso de segurança aplicado nos navegadores que evita as requisições cruzadas
- Necessário definir permissões para origem cruzadas dos navegadores. Isso é realizado nos cabecaçhos HTTP

## Habilitando CORS no controlador

- Usamos a anotação `@CrossOrigin` informando o valor do client que será liberado
- Por padrão, o spring retorna o codstatus 403 caso a origem não esteja nas regras de liberação
- Caso seja necessário liberar todas as origens usamos o valor `*`
- Essa anotação pode ser usada nos métodos como também nos controladores

## Habilitando CORS globalmente na api

- Exemplo de classe que implementa a configuração do CORS globalmente

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
                // Exemplo de permissão de alguns métodos
                .allowedMethods("GET", "POST")
                // Cache ddo Option
                .maxAge(30);
    }
}
```