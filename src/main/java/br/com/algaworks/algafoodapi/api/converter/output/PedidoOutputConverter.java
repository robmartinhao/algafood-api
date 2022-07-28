package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.controller.PedidoController;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoOutput;
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

    @Override
    public PedidoOutput toModel(Pedido pedido) {
        PedidoOutput pedidoModelOutput = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelOutput);

        pedidoModelOutput.add(algaLinks.linkToPedidos());

        pedidoModelOutput.getRestaurante().add(
                algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModelOutput.getCliente().add(
                algaLinks.linkToUsuario(pedido.getCliente().getId()));

        pedidoModelOutput.getFormaPagamento().add(
                algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoModelOutput.getEnderecoEntrega().getCidade().add(
                algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        pedidoModelOutput.getItens().forEach(item -> {
            item.add(algaLinks.linkToProduto(
                    pedidoModelOutput.getRestaurante().getId(), item.getProdutoId(), "produto"));
        });

        return pedidoModelOutput;
    }
}
