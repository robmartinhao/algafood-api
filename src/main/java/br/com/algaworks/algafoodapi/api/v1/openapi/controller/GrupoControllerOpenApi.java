package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.GrupoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Grupos")
public interface GrupoControllerOpenApi {

    @Operation(summary = "Listar Grupos")
    CollectionModel<GrupoOutput> listar();

    @Operation(summary = "Busca um grupo por Id")
    GrupoOutput buscarPeloId(@Parameter(description = "ID de um grupo", example = "1", required = true) Long grupoId);

    @Operation(summary = "Cadastra um grupo")
    GrupoOutput salvar(@RequestBody(description = "Representação de um novo grupo", required = true) GrupoInput grupoInput);

    @Operation(summary = "Atualiza um grupo por Id")
    GrupoOutput atualizar(@Parameter(description = "ID de um grupo", example = "1", required = true) Long grupoId,
                          @RequestBody(description = "Representação de um grupo com os novos dados", required = true) GrupoInput grupoInput);

    @Operation(summary = "Exclui um grupo por Id")
    ResponseEntity<Void> remover(Long grupoId);
}

