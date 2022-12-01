package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.UsuarioOutput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface RestauranteUsuarioControllerOpenApi {

    CollectionModel<UsuarioOutput> listar(Long restauranteId);

    ResponseEntity<Void> remover(Long restauranteId, Long usuarioId);

    ResponseEntity<Void> adicionar(Long restauranteId, Long usuarioId);
}
