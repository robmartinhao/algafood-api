package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import br.com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static br.com.algaworks.algafoodapi.api.v1.controller.EstatisticasController.EstatisticasModel;

public interface EstatisticasControllerOpenApi {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPdf(
            VendaDiariaFilter filtro,
            String timeOffset);

    EstatisticasModel estatisticas();
}
