package com.fredsonchaves.algafood.domain.repository;

import com.fredsonchaves.algafood.domain.entity.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listar();

    FormaPagamento buscarPorId(Long id);

    FormaPagamento adicionar(FormaPagamento formaPagamento);

    void remover(FormaPagamento formaPagamento);
}
