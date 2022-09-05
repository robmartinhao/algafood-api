package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.converter.domain.ProdutoDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.ProdutoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.ProdutoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.ProdutoOutput;
import br.com.algaworks.algafoodapi.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.model.Produto;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.ProdutoRepository;
import br.com.algaworks.algafoodapi.domain.service.ProdutoService;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoOutputConverter produtoOutputConverter;

    @Autowired
    private ProdutoDomainConverter produtoDomainConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<ProdutoOutput> listar(@PathVariable Long restauranteId, @RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        List<Produto> produtos = null;
        if (incluirInativos) {
            produtos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findAtivosByRestaurante(restaurante);
        }
        return produtoOutputConverter.toCollectionModel(produtos)
                .add(algaLinks.linkToProdutos(restauranteId));
    }

    @GetMapping(value = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoOutput buscarPeloId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        return produtoOutputConverter.toModel(produto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoOutput salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        Produto produto = produtoDomainConverter.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        return produtoOutputConverter.toModel(produtoService.salvar(produto));
    }

    @PutMapping(value = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoOutput atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoEncontrado = produtoService.buscarOuFalhar(restauranteId, produtoId);
        produtoDomainConverter.copyToDomainObject(produtoInput, produtoEncontrado);

        return produtoOutputConverter.toModel(produtoService.salvar(produtoEncontrado));
    }
}
