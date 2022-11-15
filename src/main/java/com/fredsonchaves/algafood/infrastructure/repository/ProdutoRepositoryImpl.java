package com.fredsonchaves.algafood.infrastructure.repository;

import com.fredsonchaves.algafood.domain.entity.FotoProduto;
import com.fredsonchaves.algafood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public FotoProduto save(FotoProduto fotoProduto) {
        return entityManager.merge(fotoProduto);
    }
}
