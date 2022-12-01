package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface UsuarioGrupoControllerOpenApi {

    CollectionModel<GrupoOutput> listar(Long usuarioId);

    ResponseEntity<Void> remover(Long usuarioId, Long grupoId);

    ResponseEntity<Void> adicionar(Long usuarioId, Long grupoId);
}
