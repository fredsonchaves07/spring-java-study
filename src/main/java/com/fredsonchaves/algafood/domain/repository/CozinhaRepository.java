package com.fredsonchaves.algafood.domain.repository;

import com.fredsonchaves.algafood.domain.entity.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();

    Cozinha buscarPorId(Long id);

    Cozinha salvar(Cozinha cozinha);

    void remover(Cozinha cozinha);
}
