package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.CidadeInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CidadeOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades")
public interface CidadeControllerOpenApi {

    @Operation(summary = "Lista as cidades")
    CollectionModel<CidadeOutput> listar();

    @Operation(summary = "Busca uma cidade por Id")
    CidadeOutput buscarPeloId(Long id);

    @Operation(summary = "Cadastra uma cidade", description = "Cadastro de uma cidade, necessita de um estado e nome válido")
    CidadeOutput salvar(CidadeInput cidadeInput);

    @Operation(summary = "Atualiza uma cidade por Id")
    CidadeOutput atualizar(Long id, CidadeInput cidadeInput);

    @Operation(summary = "Exclui uma cidade por Id")
    ResponseEntity<Void> remover(Long id);
}
