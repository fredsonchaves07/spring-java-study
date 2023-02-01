package com.fredsonchaves.algafood.domain.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String senha;

    @ManyToMany
    @JoinTable(
            name = "usuario_grupo",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    public List<Grupo> grupos = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    public LocalDateTime dataCadastro;


}
