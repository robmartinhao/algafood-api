package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FormaPagamentoOutput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface RestauranteFormaPagamentoControllerOpenApi {

    CollectionModel<FormaPagamentoOutput> listar(Long restauranteId);
    ResponseEntity<Void> desassociar(Long restauranteId, Long formaPagamentoId);

    ResponseEntity<Void> associar(Long restauranteId, Long formaPagamentoId);
}
