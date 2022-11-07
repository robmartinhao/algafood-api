package br.com.algaworks.algafoodapi.api.v2.openapi.controller;

import br.com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import br.com.algaworks.algafoodapi.api.v2.model.CozinhaOutputV2;
import br.com.algaworks.algafoodapi.api.v2.model.input.CozinhaInputV2;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Cozinhas")
public interface CozinhaControllerV2OpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    PagedModel<CozinhaOutputV2> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaOutputV2 buscarPeloId(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    CozinhaOutputV2 salvar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
            CozinhaInputV2 cozinhaInput);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaOutputV2 atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId,

            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados",
                    required = true)
            CozinhaInputV2 cozinhaInput);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    ResponseEntity<Void> remover(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);
}