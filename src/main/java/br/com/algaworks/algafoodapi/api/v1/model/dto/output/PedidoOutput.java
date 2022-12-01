package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoOutput extends RepresentationModel<PedidoOutput> {

    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private String status;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;

    private OffsetDateTime dataEntrega;

    private OffsetDateTime dataCancelamento;

    private RestauranteResumoOutput restauranteResumoOutput;
    private UsuarioOutput cliente;
    private FormaPagamentoOutput formaPagamento;
    private EnderecoOutput enderecoEntrega;
    private List<ItemPedidoOut> itens;
    private RestauranteApenasNomeOutput restaurante;
}
