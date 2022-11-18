package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.EstadoController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.EstadoOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class EstadoOutputConverter extends RepresentationModelAssemblerSupport<Estado, EstadoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public EstadoOutputConverter() {
        super(EstadoController.class, EstadoOutput.class);
    }

    public EstadoOutput toModel(Estado estado) {
        EstadoOutput estadoModelOutput = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModelOutput);

        if (algaSecurity.podeConsultarEstados()) {
            estadoModelOutput.add(algaLinks.linkToEstados("estados"));
        }
        return estadoModelOutput;
    }

    @Override
    public CollectionModel<EstadoOutput> toCollectionModel(Iterable<? extends Estado> entities) {
        CollectionModel<EstadoOutput> collectionModel = super.toCollectionModel(entities);
        if (algaSecurity.podeConsultarEstados()) {
            collectionModel.add(algaLinks.linkToEstados());
        }
        return collectionModel;
    }
}
