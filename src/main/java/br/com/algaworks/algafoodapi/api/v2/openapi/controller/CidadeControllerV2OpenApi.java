package br.com.algaworks.algafoodapi.api.v2.openapi.controller;

import br.com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import br.com.algaworks.algafoodapi.api.v2.model.CidadeOutputV2;
import br.com.algaworks.algafoodapi.api.v2.model.input.CidadeInputV2;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeOutputV2> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutputV2 buscarPeloId(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada"),
    })
    CidadeOutputV2 salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
            CidadeInputV2 cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutputV2 atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long cidadeId,

            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true)
            CidadeInputV2 cidadeInput);

}
