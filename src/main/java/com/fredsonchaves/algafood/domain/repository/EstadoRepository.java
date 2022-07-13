package com.fredsonchaves.algafood.domain.repository;

import com.fredsonchaves.algafood.domain.entity.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();

    Estado buscarPorId(Long id);

    Estado adicionar(Estado permissao);

    void remover(Long estadoId);
}
