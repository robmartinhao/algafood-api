package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.CidadeInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CidadeOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {
    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeOutput> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutput buscarPeloId(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada")
    })
    CidadeOutput salvar(@ApiParam(value = "Representação de uma nova cidade", required = true)
                        CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeOutput atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true)
                           Long id,
                           @ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados")
                           CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true)
            Long id);
}
