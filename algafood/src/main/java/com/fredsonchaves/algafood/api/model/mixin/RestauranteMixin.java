package com.fredsonchaves.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fredsonchaves.algafood.domain.entity.Endereco;
import com.fredsonchaves.algafood.domain.entity.FormaPagamento;
import com.fredsonchaves.algafood.domain.entity.Produto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestauranteMixin {

    @JsonIgnore
    public List<Produto> produtos = new ArrayList<>();

    @JsonIgnore
    private List<FormaPagamento> formaPagamentos = new ArrayList<>();

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;
}
