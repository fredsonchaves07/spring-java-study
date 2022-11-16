package com.fredsonchaves.algafood.domain.entity;

import javax.persistence.*;

@Entity
public class FotoProduto {

    @Id
    @Column(name = "produto_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Produto produto;

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;

    public FotoProduto() {

    }

    public Produto getProduto() {
        return produto;
    }

    public Long getRestauranteId() {
        if (getProduto() != null) return getProduto().getRestaurante().getId();
        return null;
    }
}
