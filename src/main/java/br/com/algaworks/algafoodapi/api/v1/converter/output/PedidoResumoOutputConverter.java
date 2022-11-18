package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.PedidoController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PedidoResumoOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoResumoOutputConverter() {
        super(PedidoController.class, PedidoResumoOutput.class);
    }

    @Override
    public PedidoResumoOutput toModel(Pedido pedido) {
        PedidoResumoOutput pedidoModelOutput = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModelOutput);

        if (algaSecurity.podePesquisarPedidos()) {
            pedidoModelOutput.add(algaLinks.linkToPedidos("pedidos"));
        }
        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoModelOutput.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        }
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModelOutput.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
        }
        return pedidoModelOutput;
    }
}
