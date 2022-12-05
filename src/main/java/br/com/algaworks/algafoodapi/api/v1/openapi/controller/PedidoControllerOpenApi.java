package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.PedidoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PedidoOutput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PedidoResumoOutput;
import br.com.algaworks.algafoodapi.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@SecurityRequirement(name = "security_auth")
public interface PedidoControllerOpenApi {
    PagedModel<PedidoResumoOutput> pesquisar(PedidoFilter filtro, Pageable pageable);

    PedidoOutput salvar(PedidoInput pedidoInput);

    PedidoOutput buscarPeloId(String codigoPedido);
}
