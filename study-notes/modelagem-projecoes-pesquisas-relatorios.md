# Modelagem de proje��es, pesquisas e relat�rios

## Limitando os campos retornados pela API com Squiggly

- Uma biblioteca que ajuda a limitar as propriedades do recurso
- Possui uma documenta��o e bem f�cil sua utiliza��o https://github.com/bohnman/squiggly
- Os filtros s�o usados nos par�metros da URL de forma �nica. � retornado as informa��es de acordo com os valores informados na propriedade `fields` da requisi��o
- Criamos uma classe de configura��o no pacote `squiggly`

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