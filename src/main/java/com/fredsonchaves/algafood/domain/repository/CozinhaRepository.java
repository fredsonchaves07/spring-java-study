package com.fredsonchaves.algafood.domain.repository;

import com.fredsonchaves.algafood.domain.entity.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    //List<Cozinha> consultarPorNome(String nome);
}
