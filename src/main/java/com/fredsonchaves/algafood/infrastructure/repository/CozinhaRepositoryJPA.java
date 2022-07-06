package com.fredsonchaves.algafood.infrastructure.repository;

import com.fredsonchaves.algafood.domain.entity.Cozinha;
import com.fredsonchaves.algafood.domain.repository.CozinhaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CozinhaRepositoryJPA implements CozinhaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cozinha> listar() {
        return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Override
    public Cozinha buscarPorId(Long id) {
        return entityManager.find(Cozinha.class, id);
    }

    @Override
    @Transactional
    public void remover(Cozinha cozinha) {
        cozinha = buscarPorId(cozinha.getId());
        entityManager.remove(cozinha);
    }

    @Override
    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }
}
