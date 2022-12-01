package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.GrupoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface GrupoControllerOpenApi {
    CollectionModel<GrupoOutput> listar();

    GrupoOutput buscarPeloId(Long grupoId);

    GrupoOutput salvar(GrupoInput grupoInput);

    GrupoOutput atualizar(Long grupoId, GrupoInput grupoInput);

    ResponseEntity<Void> remover(Long grupoId);
}

