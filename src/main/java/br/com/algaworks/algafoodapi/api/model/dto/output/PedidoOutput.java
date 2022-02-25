package br.com.algaworks.algafoodapi.api.model.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoOutput {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private RestauranteResumoOutput restaurante;
    private UsuarioOutput cliente;
    private FormaPagamentoOutput formaPagamento;
    private EnderecoOutput enderecoEntrega;
    private List<ItemPedidoOut> itens;

}
