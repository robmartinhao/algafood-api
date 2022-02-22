package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.output.PedidoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoOutput;
import br.com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import br.com.algaworks.algafoodapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoOutputConverter pedidoOutputConverter;

    @GetMapping
    public List<PedidoOutput> listar() {
        return pedidoOutputConverter.toCollectionPedidoOutput(pedidoRepository.findAll());
    }

    @GetMapping("/{id}")
    public PedidoOutput buscarPeloId(@PathVariable Long id) {
        return pedidoOutputConverter.toPedidoOutput(emissaoPedidoService.buscarOuFalhar(id));
    }
}
