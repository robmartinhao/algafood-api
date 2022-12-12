package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.ProdutoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.ProdutoOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @Operation(summary = "Lista os produtos de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    CollectionModel<ProdutoOutput> listar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                                          @Parameter(description = "Incluir inativos", example = "false", required = false) Boolean incluirInativos);

    @Operation(summary = "Busca um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    ProdutoOutput buscarPeloId(@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                               @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);

    @Operation(summary = "Cadastra um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    ProdutoOutput salvar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                         @RequestBody(description = "Representação de um novo produto", required = true) ProdutoInput produtoInput);

    @Operation(summary = "Atualiza um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    ProdutoOutput atualizar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
                            @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId,
                            @RequestBody(description = "Representação de um produto com os novos dados", required = true) ProdutoInput produtoInput);
}
