package com.fredsonchaves.algafood.infrastructure.repository;

import com.fredsonchaves.algafood.domain.entity.Estado;
import com.fredsonchaves.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class EstadoJPA implements EstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estado> listar() {
        return entityManager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscarPorId(Long id) {
        return entityManager.find(Estado.class, id);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        Estado estado = buscarPorId(id);
        if (estado == null) {
            throw new EmptyResultDataAccessException(1);
        }
        entityManager.remove(estado);
    }

    @Override
    @Transactional
    public Estado adicionar(Estado permissao) {
        return entityManager.merge(permissao);
    }
}
