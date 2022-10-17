package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.RestauranteProdutoFotoController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FotoProdutoOutput;
import br.com.algaworks.algafoodapi.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoOutputConverter extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public FotoProdutoOutputConverter() {
        super(RestauranteProdutoFotoController.class, FotoProdutoOutput.class);
    }

    @Override
    public FotoProdutoOutput toModel(FotoProduto foto) {
        FotoProdutoOutput fotoProdutoModel = modelMapper.map(foto, FotoProdutoOutput.class);

        fotoProdutoModel.add(algaLinks.linkToFotoProduto(
                foto.getRestauranteId(), foto.getProduto().getId()));

        fotoProdutoModel.add(algaLinks.linkToProduto(
                foto.getRestauranteId(), foto.getProduto().getId(), "produto"));

        return fotoProdutoModel;
    }

}
