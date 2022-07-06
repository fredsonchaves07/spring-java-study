package com.fredsonchaves.algafood.infrastructure.repository;

import com.fredsonchaves.algafood.domain.entity.Permissao;
import com.fredsonchaves.algafood.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class PermissaoJPA implements PermissaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Permissao> listar() {
        return entityManager.createQuery("from Permissao", Permissao.class).getResultList();
    }

    @Override
    public Permissao buscarPorId(Long id) {
        return entityManager.find(Permissao.class, id);
    }

    @Override
    @Transactional
    public void remover(Permissao permissao) {
        permissao = buscarPorId(permissao.getId());
        entityManager.remove(permissao);
    }

    @Override
    @Transactional
    public Permissao adicionar(Permissao permissao) {
        return entityManager.merge(permissao);
    }
}
