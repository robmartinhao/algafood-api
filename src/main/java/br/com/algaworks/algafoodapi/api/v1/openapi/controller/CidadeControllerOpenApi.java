package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.CidadeInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CidadeOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Lista as cidades")
    CollectionModel<CidadeOutput> listar();

    @Operation(summary = "Busca uma cidade por Id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID da cidade inválido",
            content = @Content(schema = @Schema))
    })
    CidadeOutput buscarPeloId(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long id);

    @Operation(summary = "Cadastra uma cidade", description = "Cadastro de uma cidade, necessita de um estado e nome válido")
    CidadeOutput salvar(@RequestBody(description = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

    @Operation(summary = "Atualiza uma cidade por Id")
    CidadeOutput atualizar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long id,
                           @RequestBody(description = "Representação de uma cidade com dados atualizados", required = true) CidadeInput cidadeInput);

    @Operation(summary = "Exclui uma cidade por Id")
    ResponseEntity<Void> remover(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long id);
}
