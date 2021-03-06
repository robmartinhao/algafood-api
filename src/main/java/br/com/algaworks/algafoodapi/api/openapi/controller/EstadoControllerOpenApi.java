package br.com.algaworks.algafoodapi.api.openapi.controller;

import br.com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import br.com.algaworks.algafoodapi.api.model.dto.input.EstadoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.EstadoOutput;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    List<EstadoOutput> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoOutput buscarPeloId(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado cadastrado"),
    })
    EstadoOutput salvar(
            @ApiParam(name = "corpo", value = "Representação de um novo estado", required = true)
            EstadoInput estadoInput);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoOutput atualizar(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId,

            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true)
            EstadoInput estadoInput);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de um estado", example = "1", required = true)
            Long estadoId);
}
