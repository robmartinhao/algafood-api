package br.com.algaworks.algafoodapi.api.openapi.controller;

import br.com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import br.com.algaworks.algafoodapi.api.model.dto.input.GrupoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.GrupoOutput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
    @ApiOperation("Lista os grupos")
    CollectionModel<GrupoOutput> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoOutput buscarPeloId(
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    GrupoOutput salvar(
            @ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true)
            GrupoInput grupoInput);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GrupoOutput atualizar(
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId,

            @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados")
            GrupoInput grupoInput);

    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "ID de um grupo", example = "1", required = true)
            Long grupoId);
}

