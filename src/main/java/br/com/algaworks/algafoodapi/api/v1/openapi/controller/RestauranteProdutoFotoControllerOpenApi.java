package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.FotoProdutoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FotoProdutoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoFotoControllerOpenApi {

    @Operation(summary = "Atualiza a foto do produto de um restaurante")
    FotoProdutoOutput atualizarFoto(@Parameter(description = "Id do restaurante", example = "1", required = true) Long restauranteId,
                                    @Parameter(description = "Id do produto", example = "2", required = true) Long produtoId,
                                    @RequestBody(required = true) FotoProdutoInput fotoProdutoInput)
            throws IOException;

    ResponseEntity<Void> excluir(Long restauranteId, Long produtoId);

    @Operation(summary = "Busca foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoOutput.class)),
                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
            })
    })
    FotoProdutoOutput pesquisar(Long restauranteId, Long produtoId);

    @Operation(hidden = true)
    ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;
}
