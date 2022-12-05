package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PermissaoOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenApi {

    CollectionModel<PermissaoOutput> listar(Long grupoId);

    ResponseEntity<Void> remover(Long grupoId, Long permissaoId);

    ResponseEntity<Void> adicionar(Long grupoId, Long permissaoId);
}