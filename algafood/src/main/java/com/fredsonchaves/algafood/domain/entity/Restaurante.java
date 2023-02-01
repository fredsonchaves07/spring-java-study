package com.fredsonchaves.algafood.domain.entity;

import com.fredsonchaves.algafood.core.validation.Groups;
import com.fredsonchaves.algafood.core.validation.Multiplo;
import com.fredsonchaves.algafood.core.validation.ValorZeroIncluiDescricao;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotEmpty
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @DecimalMin(value = "1")
//    @TaxaFrete
    @Multiplo(numero = 5)
    private BigDecimal taxaFrete;

    @OneToMany(mappedBy = "restaurante")
    public List<Produto> produtos = new ArrayList<>();

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    private Cozinha cozinha;

    @ManyToMany
    @JoinTable(
            name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    private Set<FormaPagamento> formaPagamentos = new HashSet<>();

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cozinha getCozinha() {
        return cozinha;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public Set<FormaPagamento> getFormaPagamentos() {
        return formaPagamentos;
    }

    public void setTaxaFrete(BigDecimal taxaFrete) {
        this.taxaFrete = taxaFrete;
    }

    public void setCozinha(Cozinha cozinha) {
        this.cozinha = cozinha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurante)) return false;
        Restaurante that = (Restaurante) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean removerFormaPagamento(FormaPagamento formaPagamento) {
        return getFormaPagamentos().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
        return getFormaPagamentos().add(formaPagamento);
    }
}
