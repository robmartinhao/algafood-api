package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.controller.PedidoController;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoResumoOutput;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoOutputConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoResumoOutputConverter() {
        super(PedidoController.class, PedidoResumoOutput.class);
    }

    @Override
    public PedidoResumoOutput toModel(Pedido pedido) {
        PedidoResumoOutput pedidoModelOutput = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelOutput);

        pedidoModelOutput.add(algaLinks.linkToPedidos());

        pedidoModelOutput.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModelOutput.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

        return pedidoModelOutput;
    }
}
