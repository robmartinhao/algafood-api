package br.com.algaworks.algafoodapi.api.v2.openapi.controller;

import br.com.algaworks.algafoodapi.api.v2.model.CidadeOutputV2;
import br.com.algaworks.algafoodapi.api.v2.model.input.CidadeInputV2;
import org.springframework.hateoas.CollectionModel;

public interface CidadeControllerV2OpenApi {

    CollectionModel<CidadeOutputV2> listar();

    CidadeOutputV2 buscarPeloId(
            Long cidadeId);

    CidadeOutputV2 salvar(
            CidadeInputV2 cidadeInput);

    CidadeOutputV2 atualizar(
            Long cidadeId,

            CidadeInputV2 cidadeInput);
}
