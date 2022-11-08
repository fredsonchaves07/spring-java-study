package com.fredsonchaves.algafood.domain.service;

import com.fredsonchaves.algafood.domain.entity.dto.VendaDiaria;
import com.fredsonchaves.algafood.domain.filter.VendaDiariaFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter);
}
