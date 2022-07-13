package com.fredsonchaves.algafood.domain.repository;

import com.fredsonchaves.algafood.domain.entity.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();

    Restaurante buscarPorId(Long id);

    Restaurante salvar(Restaurante restaurante);

    void remover(Long id);
}
