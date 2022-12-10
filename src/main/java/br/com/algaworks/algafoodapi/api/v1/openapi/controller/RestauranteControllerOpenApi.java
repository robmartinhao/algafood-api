package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.RestauranteInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteApenasNomeOutput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteBasicoOutput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {

    @Operation(parameters = {
            @Parameter(name = "projecao",
            description = "Nome da projeção",
            example = "apenas-nome",
            in = ParameterIn.QUERY,
            required = true)
    })
    CollectionModel<RestauranteBasicoOutput> listar();

    @Operation(hidden = true)
    CollectionModel<RestauranteApenasNomeOutput> listarApenasNomes();

    RestauranteOutput buscarPeloId(Long restauranteId);

    RestauranteOutput salvar(RestauranteInput restauranteInput);

    RestauranteOutput atualizar(Long restauranteId, RestauranteInput restauranteInput);

    ResponseEntity<Void> ativar(Long restauranteId);

    ResponseEntity<Void> inativar(Long restauranteId);

    ResponseEntity<Void> ativarMultiplos(List<Long> restauranteIds);

    ResponseEntity<Void> inativarMultiplos(List<Long> restauranteIds);

    ResponseEntity<Void> abertura(Long restauranteId);

    ResponseEntity<Void> fechamento(Long restauranteId);
}
