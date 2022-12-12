package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.SenhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.UsuarioComSenhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.UsuarioInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.UsuarioOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Usuários")
public interface UsuarioControllerOpenApi {

    @Operation(summary = "Lista os usuários")
    List<UsuarioOutput> listar();

    @Operation(summary = "Busca um usuário por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
    })
    UsuarioOutput buscarPeloId(@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId);

    @Operation(summary = "Cadastra um usuário", responses = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
    })
    UsuarioOutput salvar(@RequestBody(description = "Representação de um novo usuário", required = true) UsuarioComSenhaInput usuarioInput);

    @Operation(summary = "Atualiza um usuário por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) })
    })
    UsuarioOutput atualizar(@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId,
                            @RequestBody(description = "Representação de um usuário com os novos dados", required = true) UsuarioInput usuarioInput);

    @Operation(summary = "Atualiza a senha de um usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema")) })
    })
    ResponseEntity<Void> atualizarSenha(@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId,
                                        @RequestBody(description = "Representação de uma nova senha", required = true) SenhaInput senha);
}
