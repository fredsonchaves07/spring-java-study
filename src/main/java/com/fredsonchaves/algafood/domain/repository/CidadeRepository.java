package com.fredsonchaves.algafood.domain.repository;

import com.fredsonchaves.algafood.domain.entity.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();

    Cidade buscarPorId(Long id);

    Cidade adicionar(Cidade permissao);

    void remover(Long cidadeId);
}
