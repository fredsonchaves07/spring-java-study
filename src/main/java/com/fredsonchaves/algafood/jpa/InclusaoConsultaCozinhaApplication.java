package com.fredsonchaves.algafood.jpa;

import com.fredsonchaves.algafood.AlgafoodApplication;
import com.fredsonchaves.algafood.domain.entity.Cozinha;
import com.fredsonchaves.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class InclusaoConsultaCozinhaApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
        Cozinha cozinha1 = new Cozinha();
        Cozinha cozinha2 = new Cozinha();
        cozinha1.setNome("Brasileira");
        cozinha2.setNome("Japonesa");
        cadastroCozinha.salvar(cozinha1);
        cadastroCozinha.salvar(cozinha2);
        List<Cozinha> cozinhas = cadastroCozinha.listar();
        for (Cozinha cozinha : cozinhas) System.out.println(cozinha.getNome());
    }
}
