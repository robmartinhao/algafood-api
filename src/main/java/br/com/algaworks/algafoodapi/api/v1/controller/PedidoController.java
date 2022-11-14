package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.domain.PedidoDomainConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.PedidoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.PedidoResumoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.PedidoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PedidoOutput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PedidoResumoOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.PedidoControllerOpenApi;
import br.com.algaworks.algafoodapi.core.data.PageWrapper;
import br.com.algaworks.algafoodapi.core.data.PageableTranslator;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.core.security.CheckSecurity;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.filter.PedidoFilter;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import br.com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import br.com.algaworks.algafoodapi.domain.service.EmissaoPedidoService;
import br.com.algaworks.algafoodapi.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

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

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @Autowired
    private AlgaSecurity algaSecurity;

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

    @CheckSecurity.Pedidos.PodePesquisar
    @GetMapping
    public PagedModel<PedidoResumoOutput> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        Pageable pageableTraduzido = traduzirPageable(pageable);
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoOutputConverter);
    }

    @CheckSecurity.Pedidos.PodeBuscar
    @GetMapping("/{codigoPedido}")
    public PedidoOutput buscarPeloId(@PathVariable String codigoPedido) {
        return pedidoOutputConverter.toModel(emissaoPedidoService.buscarOuFalhar(codigoPedido));
    }

    @CheckSecurity.Pedidos.PodeCriar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoOutput salvar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoDomainConverter.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(algaSecurity.getUsuarioId());

            novoPedido = emissaoPedidoService.emitir(novoPedido);

            return pedidoOutputConverter.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "nomerestaurante", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}
