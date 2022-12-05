package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.UsuarioOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface RestauranteUsuarioControllerOpenApi {

    CollectionModel<UsuarioOutput> listar(Long restauranteId);

    ResponseEntity<Void> remover(Long restauranteId, Long usuarioId);

    ResponseEntity<Void> adicionar(Long restauranteId, Long usuarioId);
}
