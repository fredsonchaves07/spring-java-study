package com.fredsonchaves.algafood.jpa;

import com.fredsonchaves.algafood.domain.entity.Cozinha;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cozinha> listar() {
        return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    public Cozinha buscar(Long id) {
        return entityManager.find(Cozinha.class, id);
    }

    @Transactional
    public void remover(Cozinha cozinha) {
        cozinha = buscar(cozinha.getId());
        entityManager.remove(cozinha);
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }
}
