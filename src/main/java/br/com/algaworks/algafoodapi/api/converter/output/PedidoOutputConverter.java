package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.controller.*;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoOutput;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoOutputConverter extends RepresentationModelAssemblerSupport<Pedido, PedidoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoOutputConverter() {
        super(PedidoController.class, PedidoOutput.class);
    }

    @Override
    public PedidoOutput toModel(Pedido pedido) {
        PedidoOutput pedidoModelOutput = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelOutput);

        pedidoModelOutput.add(linkTo(PedidoController.class).withRel("pedidos"));

        pedidoModelOutput.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscarPeloId(pedido.getRestaurante().getId())).withSelfRel());

        pedidoModelOutput.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscarPeloId(pedido.getCliente().getId())).withSelfRel());

        // Passamos null no segundo argumento, porque é indiferente para a
        // construção da URL do recurso de forma de pagamento
        pedidoModelOutput.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .buscarPeloId(pedido.getFormaPagamento().getId(), null)).withSelfRel());

        pedidoModelOutput.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                .buscarPeloId(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());

        pedidoModelOutput.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutoController.class)
                    .buscarPeloId(pedidoModelOutput.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
        });

        return pedidoModelOutput;
    }
}
