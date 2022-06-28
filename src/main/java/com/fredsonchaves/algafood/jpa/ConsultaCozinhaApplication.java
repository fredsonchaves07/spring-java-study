package com.fredsonchaves.algafood.jpa;

import com.fredsonchaves.algafood.AlgafoodApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import com.fredsonchaves.algafood.domain.entity.*;

import java.util.List;

public class ConsultaCozinhaApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
        List<Cozinha> cozinhas = cadastroCozinha.listar();
        for (Cozinha cozinha: cozinhas) System.out.println(cozinha.getNome());
    }
}
