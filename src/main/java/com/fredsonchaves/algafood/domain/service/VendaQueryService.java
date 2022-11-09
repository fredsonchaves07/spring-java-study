package com.fredsonchaves.algafood.domain.service;

import com.fredsonchaves.algafood.domain.entity.dto.VendaDiaria;
import com.fredsonchaves.algafood.domain.filter.VendaDiariaFilter;

import java.util.List;


public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);
}
