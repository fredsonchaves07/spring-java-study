package com.fredsonchaves.algafood.domain.repository;

import com.fredsonchaves.algafood.domain.entity.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> listar();

    Permissao buscarPorId(Long id);

    Permissao adicionar(Permissao permissao);

    void remover(Permissao permissao);
}
