package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.ProdutoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.ProdutoOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenApi {

    CollectionModel<ProdutoOutput> listar(Long restauranteId, Boolean incluirInativos);

    ProdutoOutput buscarPeloId(Long restauranteId, Long produtoId);

    ProdutoOutput salvar(Long restauranteId, ProdutoInput produtoInput);

    ProdutoOutput atualizar(Long restauranteId, Long produtoId, ProdutoInput produtoInput);
}
