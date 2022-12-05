package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.GrupoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface GrupoControllerOpenApi {
    CollectionModel<GrupoOutput> listar();

    GrupoOutput buscarPeloId(Long grupoId);

    GrupoOutput salvar(GrupoInput grupoInput);

    GrupoOutput atualizar(Long grupoId, GrupoInput grupoInput);

    ResponseEntity<Void> remover(Long grupoId);
}

