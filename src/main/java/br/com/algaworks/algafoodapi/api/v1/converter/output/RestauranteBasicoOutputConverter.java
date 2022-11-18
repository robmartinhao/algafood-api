package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.RestauranteController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteBasicoOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoOutputConverter
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestauranteBasicoOutputConverter() {
        super(RestauranteController.class, RestauranteBasicoOutput.class);
    }

    @Override
    public RestauranteBasicoOutput toModel(Restaurante restaurante) {
        RestauranteBasicoOutput restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);
        if (algaSecurity.podeConsultarRestaurantes()) {

            restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        }
        if (algaSecurity.podeConsultarCozinhas()) {

            restauranteModel.getCozinha().add(
                    algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoOutput> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteBasicoOutput> collectionModel = super.toCollectionModel(entities);
        if (algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(algaLinks.linkToRestaurantes());
        }
        return collectionModel;
    }
}