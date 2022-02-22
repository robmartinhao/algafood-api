package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoResumoOutput;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoOutput toPedidoResumoOutput(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoOutput.class);
    }

    public List<PedidoResumoOutput> toCollectionPedidoResumoOutput(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::toPedidoResumoOutput)
                .collect(Collectors.toList());
    }
}
