package com.fredsonchaves.algafood.domain.service;

import com.fredsonchaves.algafood.domain.filter.VendaDiariaFilter;
import net.sf.jasperreports.engine.JRException;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) throws JRException;
}
