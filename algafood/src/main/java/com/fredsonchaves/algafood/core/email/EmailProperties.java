package com.fredsonchaves.algafood.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {


    private String remente;

    public String getRemente() {
        return remente;
    }

    public void setRemente(String remente) {
        this.remente = remente;
    }
}
