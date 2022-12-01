package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoOutput extends RepresentationModel<PedidoResumoOutput> {

    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private String status;

    private OffsetDateTime dataCriacao;
    private RestauranteResumoOutput restauranteResumoOutput;
    private UsuarioOutput cliente;

    private RestauranteApenasNomeOutput restaurante;
}
