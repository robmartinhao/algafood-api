package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.PedidoDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.PedidoOutputConverter;
import br.com.algaworks.algafoodapi.api.converter.output.PedidoResumoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.PedidoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoOutput;
import br.com.algaworks.algafoodapi.api.model.dto.output.PedidoResumoOutput;
import br.com.algaworks.algafoodapi.core.data.PageableTranslator;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.Pedido;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import br.com.algaworks.algafoodapi.domain.repository.PedidoRepository;
import br.com.algaworks.algafoodapi.domain.filter.PedidoFilter;
import br.com.algaworks.algafoodapi.domain.service.EmissaoPedidoService;
import br.com.algaworks.algafoodapi.infrastructure.repository.spec.PedidoSpecs;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @GetMapping
    public Page<PedidoResumoOutput> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        pageable = traduzirPageable(pageable);
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
        List<PedidoResumoOutput> pedidosResumoOutput = pedidoResumoOutputConverter.toCollectionPedidoResumoOutput(pedidosPage.getContent());
        Page<PedidoResumoOutput> pedidosResumoOutputPage = new PageImpl<>(pedidosResumoOutput, pageable, pedidosPage.getTotalElements());
        return pedidosResumoOutputPage;
    }

    @GetMapping("/{codigoPedido}")
    public PedidoOutput buscarPeloId(@PathVariable String codigoPedido) {
        return pedidoOutputConverter.toPedidoOutput(emissaoPedidoService.buscarOuFalhar(codigoPedido));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
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

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }

}
