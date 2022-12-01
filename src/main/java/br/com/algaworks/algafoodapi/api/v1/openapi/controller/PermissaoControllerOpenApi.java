package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PermissaoOutput;
import org.springframework.hateoas.CollectionModel;

public interface PermissaoControllerOpenApi {
    CollectionModel<PermissaoOutput> listar();
}
