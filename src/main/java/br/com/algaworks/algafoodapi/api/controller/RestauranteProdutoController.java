package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.ProdutoDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.ProdutoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.ProdutoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.ProdutoOutput;
import br.com.algaworks.algafoodapi.domain.model.Produto;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.ProdutoRepository;
import br.com.algaworks.algafoodapi.domain.service.ProdutoService;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

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

    @GetMapping
    public List<ProdutoOutput> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        List<Produto> produtos = produtoRepository.findByRestaurante(restaurante);
        return produtoOutputConverter.toCollectionProdutoOutput(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoOutput buscarPeloId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        return produtoOutputConverter.toProdutoOutput(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoOutput salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        Produto produto = produtoDomainConverter.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        return produtoOutputConverter.toProdutoOutput(produtoService.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    public ProdutoOutput atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoEncontrado = produtoService.buscarOuFalhar(restauranteId, produtoId);
        produtoDomainConverter.copyToDomainObject(produtoInput, produtoEncontrado);

        return produtoOutputConverter.toProdutoOutput(produtoService.salvar(produtoEncontrado));
    }


}
