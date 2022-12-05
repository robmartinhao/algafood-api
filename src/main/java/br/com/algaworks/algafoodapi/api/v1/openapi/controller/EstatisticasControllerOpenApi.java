package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.domain.filter.VendaDiariaFilter;
import br.com.algaworks.algafoodapi.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static br.com.algaworks.algafoodapi.api.v1.controller.EstatisticasController.EstatisticasModel;
@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPdf(
            VendaDiariaFilter filtro,
            String timeOffset);

    EstatisticasModel estatisticas();
}
