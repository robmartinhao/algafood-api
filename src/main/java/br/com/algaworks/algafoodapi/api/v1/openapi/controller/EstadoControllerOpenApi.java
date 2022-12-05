package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.EstadoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.EstadoOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {

    CollectionModel<EstadoOutput> listar();

    EstadoOutput buscarPeloId(Long estadoId);

    EstadoOutput salvar(EstadoInput estadoInput);

    EstadoOutput atualizar(Long estadoId, EstadoInput estadoInput);

    ResponseEntity<Void> remover(Long estadoId);
}
