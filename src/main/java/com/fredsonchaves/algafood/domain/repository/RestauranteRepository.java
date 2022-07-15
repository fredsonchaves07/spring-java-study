package com.fredsonchaves.algafood.domain.repository;

import com.fredsonchaves.algafood.domain.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
