package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.PedidoController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PedidoOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoOutputConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoOutputConverter() {
        super(PedidoController.class, PedidoOutput.class);
    }

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    public PedidoOutput toModel(Pedido pedido) {
        PedidoOutput pedidoModelOutput = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelOutput);

        if (algaSecurity.podePesquisarPedidos()) {
            pedidoModelOutput.add(algaLinks.linkToPedidos("pedidos"));
        }

        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModelOutput.add(algaLinks.linkToConfirmarPedido(pedido.getCodigo(), "confirmar"));
            }
            if (pedido.podeSerEntregue()) {
                pedidoModelOutput.add(algaLinks.linkToEntregarPedido(pedido.getCodigo(), "entregar"));
            }
            if (pedido.podeSerCancelado()) {
                pedidoModelOutput.add(algaLinks.linkToCancelarPedido(pedido.getCodigo(), "cancelar"));
            }
        }

        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoModelOutput.getRestaurante().add(
                    algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {

            pedidoModelOutput.getCliente().add(
                    algaLinks.linkToUsuario(pedido.getCliente().getId()));
        }
        if (algaSecurity.podeConsultarFormasPagamento()) {
            pedidoModelOutput.getFormaPagamento().add(
                    algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }

        if (algaSecurity.podeConsultarCidades()) {
            pedidoModelOutput.getEnderecoEntrega().getCidade().add(
                    algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }
        if (algaSecurity.podeConsultarCidades()) {
            pedidoModelOutput.getItens().forEach(item -> item.add(algaLinks.linkToProduto(
                    pedidoModelOutput.getRestaurante().getId(), item.getProdutoId(), "produto")));
        }
        return pedidoModelOutput;
    }
}
