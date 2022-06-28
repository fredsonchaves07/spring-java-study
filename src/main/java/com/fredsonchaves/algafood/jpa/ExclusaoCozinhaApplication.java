package com.fredsonchaves.algafood.jpa;

import com.fredsonchaves.algafood.AlgafoodApplication;
import com.fredsonchaves.algafood.domain.entity.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ExclusaoCozinhaApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setId(1L);
        cadastroCozinha.remover(cozinha1);
        List<Cozinha> cozinhas = cadastroCozinha.listar();
        for (Cozinha cozinha: cozinhas) System.out.println(cozinha.getNome());
    }
}
