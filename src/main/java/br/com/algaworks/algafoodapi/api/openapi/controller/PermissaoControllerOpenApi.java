package br.com.algaworks.algafoodapi.api.openapi.controller;

import br.com.algaworks.algafoodapi.api.model.dto.output.PermissaoOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {
    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoOutput> listar();
}
