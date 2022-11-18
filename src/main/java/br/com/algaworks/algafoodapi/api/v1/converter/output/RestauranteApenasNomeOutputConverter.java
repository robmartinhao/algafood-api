package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.RestauranteController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteApenasNomeOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeOutputConverter
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteApenasNomeOutputConverter() {
        super(RestauranteController.class, RestauranteApenasNomeOutput.class);
    }

    @Override
    public RestauranteApenasNomeOutput toModel(Restaurante restaurante) {
        RestauranteApenasNomeOutput restauranteApenasNomeOutput = createModelWithId(
                restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteApenasNomeOutput);

        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteApenasNomeOutput.add(algaLinks.linkToRestaurantes("restaurantes"));
        }
        return restauranteApenasNomeOutput;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeOutput> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteApenasNomeOutput> collectionModel = super.toCollectionModel(entities);
        if (algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(algaLinks.linkToRestaurantes());
        }
        return collectionModel;
    }
}
