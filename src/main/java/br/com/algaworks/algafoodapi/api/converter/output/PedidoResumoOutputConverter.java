package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.controller.PedidoController;
import br.com.algaworks.algafoodapi.api.controller.RestauranteController;
import br.com.algaworks.algafoodapi.api.controller.UsuarioController;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoResumoOutput;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoResumoOutputConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoOutputConverter() {
        super(PedidoController.class, PedidoResumoOutput.class);
    }

    @Override
    public PedidoResumoOutput toModel(Pedido pedido) {
        PedidoResumoOutput pedidoModelOutput = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelOutput);

        pedidoModelOutput.add(linkTo(PedidoController.class).withRel("pedidos"));

        pedidoModelOutput.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscarPeloId(pedido.getRestaurante().getId())).withSelfRel());

        pedidoModelOutput.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscarPeloId(pedido.getCliente().getId())).withSelfRel());

        return pedidoModelOutput;
    }
}
