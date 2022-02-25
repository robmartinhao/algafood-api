package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.PedidoDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.PedidoOutputConverter;
import br.com.algaworks.algafoodapi.api.converter.output.PedidoResumoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.PedidoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoOutput;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoResumoOutput;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import br.com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import br.com.algaworks.algafoodapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private PedidoResumoOutputConverter pedidoResumoOutputConverter;

    @Autowired
    private PedidoDomainConverter pedidoDomainConverter;

    @GetMapping
    public List<PedidoResumoOutput> listar() {
        return pedidoResumoOutputConverter.toCollectionPedidoResumoOutput(pedidoRepository.findAll());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoOutput buscarPeloId(@PathVariable String codigoPedido) {
        return pedidoOutputConverter.toPedidoOutput(emissaoPedidoService.buscarOuFalhar(codigoPedido));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoOutput salvar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoDomainConverter.toDomainObject(pedidoInput);

            //TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);

            return pedidoOutputConverter.toPedidoOutput(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
