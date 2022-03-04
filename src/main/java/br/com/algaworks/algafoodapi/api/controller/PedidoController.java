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
import br.com.algaworks.algafoodapi.domain.repository.filter.PedidoFilter;
import br.com.algaworks.algafoodapi.domain.service.EmissaoPedidoService;
import br.com.algaworks.algafoodapi.infrastructure.repository.spec.PedidoSpecs;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
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

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        List<Pedido> pedidos = pedidoRepository.findAll();
//        List<PedidoResumoOutput> pedidosOutput = pedidoResumoOutputConverter.toCollectionPedidoResumoOutput(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosOutput);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        SimpleFilterProvider filters = filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.isNotBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filters);
//
//        return pedidosWrapper;
//    }

    @GetMapping
    public Page<PedidoResumoOutput> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        List<PedidoResumoOutput> pedidosResumoOutput = pedidoResumoOutputConverter.toCollectionPedidoResumoOutput(pedidosPage.getContent());
        Page<PedidoResumoOutput> pedidosResumoOutputPage = new PageImpl<>(pedidosResumoOutput, pageable, pedidosPage.getTotalElements());
        return pedidosResumoOutputPage;
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
