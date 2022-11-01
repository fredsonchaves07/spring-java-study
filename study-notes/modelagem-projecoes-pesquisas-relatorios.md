# Modelagem de projeções, pesquisas e relatórios

## Limitando os campos retornados pela API com Squiggly

- Uma biblioteca que ajuda a limitar as propriedades do recurso
- Possui uma documentação e bem fácil sua utilização https://github.com/bohnman/squiggly
- Os filtros são usados nos parâmetros da URL de forma única. É retornado as informações de acordo com os valores informados na propriedade `fields` da requisição
- Criamos uma classe de configuração no pacote `squiggly`

```java
@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider());
        var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setOrder(1);
        return filterRegistration;
    }
}
```