package com.fredsonchaves.algafood.jpa;

import com.fredsonchaves.algafood.AlgafoodApplication;
import com.fredsonchaves.algafood.domain.entity.Cozinha;
import com.fredsonchaves.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class AlteracaoCozinhaApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setId(1L);
        cozinha1.setNome("Brasileira");
        cadastroCozinha.adicionar(cozinha1);
        List<Cozinha> cozinhas = cadastroCozinha.listar();
        for (Cozinha cozinha: cozinhas) System.out.println(cozinha.getNome());
    }
}
