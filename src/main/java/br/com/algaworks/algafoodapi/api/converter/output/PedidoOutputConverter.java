package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoOutput;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoOutput toPedidoOutput(Pedido pedido) {
        return modelMapper.map(pedido, PedidoOutput.class);
    }

    public List<PedidoOutput> toCollectionPedidoOutput(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toPedidoOutput)
                .collect(Collectors.toList());
    }
}
