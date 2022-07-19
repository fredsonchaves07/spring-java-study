package com.fredsonchaves.algafood.infrastructure.repository;

import com.fredsonchaves.algafood.domain.entity.Restaurante;
import com.fredsonchaves.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var jpql = new StringBuilder();
        var params = new HashMap<String, Object>();
        jpql.append("from Restaurante where 0 = 0 ");
        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            params.put("nome", "%" + nome + "%");
        }
        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            params.put("taxaInicial", taxaFreteInicial);
        }
        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            params.put("taxaFinal", taxaFreteFinal);
        }
        TypedQuery<Restaurante> query = entityManager
                .createQuery(jpql.toString(), Restaurante.class);

        params.forEach(query::setParameter);
        return query.getResultList();
    }
}
