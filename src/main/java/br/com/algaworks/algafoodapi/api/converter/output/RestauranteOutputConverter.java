package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.controller.RestauranteController;
import br.com.algaworks.algafoodapi.api.model.dto.output.RestauranteOutput;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteOutputConverter extends RepresentationModelAssemblerSupport<Restaurante, RestauranteOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteOutputConverter() {
        super(RestauranteController.class, RestauranteOutput.class);
    }

    @Override
    public RestauranteOutput toModel(Restaurante restaurante) {
        RestauranteOutput restauranteOutput = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteOutput);

        restauranteOutput.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteOutput.getCozinha().add(
                algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        restauranteOutput.getEndereco().getCidade().add(
                algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

        restauranteOutput.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(),
                "formas-pagamento"));

        restauranteOutput.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(),
                "responsaveis"));

        return restauranteOutput;
    }

    @Override
    public CollectionModel<RestauranteOutput> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }
}
