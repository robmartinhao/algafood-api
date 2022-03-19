package br.com.algaworks.algafoodapi.domain.service;

import br.com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
