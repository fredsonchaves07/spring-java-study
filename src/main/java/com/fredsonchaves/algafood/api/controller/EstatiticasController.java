package com.fredsonchaves.algafood.api.controller;

import com.fredsonchaves.algafood.domain.entity.dto.VendaDiaria;
import com.fredsonchaves.algafood.domain.filter.VendaDiariaFilter;
import com.fredsonchaves.algafood.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatiticas")
public class EstatiticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter vendaDiariaFilter) {
        return vendaQueryService.consultarVendasDiarias(vendaDiariaFilter);
    }
}
