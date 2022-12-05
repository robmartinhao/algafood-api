package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FormaPagamentoOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface RestauranteFormaPagamentoControllerOpenApi {

    CollectionModel<FormaPagamentoOutput> listar(Long restauranteId);
    ResponseEntity<Void> desassociar(Long restauranteId, Long formaPagamentoId);

    ResponseEntity<Void> associar(Long restauranteId, Long formaPagamentoId);
}
