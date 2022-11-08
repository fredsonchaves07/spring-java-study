package com.fredsonchaves.algafood.domain.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

public class VendaDiariaFilter {

    private Long restauranteId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public OffsetDateTime getDataCriacaoInicio() {
        return dataCriacaoInicio;
    }

    public void setDataCriacaoInicio(OffsetDateTime dataCriacaoInicio) {
        this.dataCriacaoInicio = dataCriacaoInicio;
    }

    public OffsetDateTime getDataCriacaoFim() {
        return dataCriacaoFim;
    }

    public void setDataCriacaoFim(OffsetDateTime dataCriacaoFim) {
        this.dataCriacaoFim = dataCriacaoFim;
    }
}
