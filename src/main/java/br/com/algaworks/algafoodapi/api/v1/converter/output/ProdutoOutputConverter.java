package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.RestauranteProdutoController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.ProdutoOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoOutputConverter extends RepresentationModelAssemblerSupport<Produto, ProdutoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public ProdutoOutputConverter() {
        super(RestauranteProdutoController.class, ProdutoOutput.class);
    }

    @Override
    public ProdutoOutput toModel(Produto produto) {
        ProdutoOutput produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));

            produtoModel.add(algaLinks.linkToFotoProduto(
                    produto.getRestaurante().getId(), produto.getId(), "foto"));
        }
        return produtoModel;
    }
}
