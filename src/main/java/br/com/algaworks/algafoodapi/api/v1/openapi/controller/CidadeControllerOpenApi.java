package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.CidadeInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CidadeOutput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface CidadeControllerOpenApi {
    CollectionModel<CidadeOutput> listar();

    CidadeOutput buscarPeloId(Long id);

    CidadeOutput salvar(CidadeInput cidadeInput);

    CidadeOutput atualizar(Long id, CidadeInput cidadeInput);

    ResponseEntity<Void> remover(Long id);
}
