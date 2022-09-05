package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.controller.RestauranteProdutoController;
import br.com.algaworks.algafoodapi.api.model.dto.output.ProdutoOutput;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import br.com.algaworks.algafoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoOutputConverter extends RepresentationModelAssemblerSupport<Produto, ProdutoOutput>{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public ProdutoOutputConverter() {
        super(RestauranteProdutoController.class, ProdutoOutput.class);
    }
    @Override
    public ProdutoOutput toModel(Produto produto) {
        ProdutoOutput produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModel);
        produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        return produtoModel;
    }
}
