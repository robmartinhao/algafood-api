package br.com.algaworks.algafoodapi.api.openapi.controller;

import br.com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import br.com.algaworks.algafoodapi.api.model.dto.input.CozinhaInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    public Page<CozinhaOutput> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaOutput buscar(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    public CozinhaOutput salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
            CozinhaInput cozinhaInput);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaOutput atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId,

            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados")
            CozinhaInput cozinhaInput);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public void remover(
            @ApiParam(value = "ID de uma cozinha", example = "1")
            Long cozinhaId);
}
