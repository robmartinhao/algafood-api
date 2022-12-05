package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface UsuarioGrupoControllerOpenApi {

    CollectionModel<GrupoOutput> listar(Long usuarioId);

    ResponseEntity<Void> remover(Long usuarioId, Long grupoId);

    ResponseEntity<Void> adicionar(Long usuarioId, Long grupoId);
}
