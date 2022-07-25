package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.controller.CidadeController;
import br.com.algaworks.algafoodapi.api.controller.EstadoController;
import br.com.algaworks.algafoodapi.api.model.dto.output.CidadeOutput;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CidadeOutputConverter extends RepresentationModelAssemblerSupport<Cidade, CidadeOutput> {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeOutputConverter() {
        super(CidadeController.class, CidadeOutput.class);
    }

    @Override
    public CidadeOutput toModel(Cidade cidade) {

        CidadeOutput cidadeModelOutput = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModelOutput);

//        CidadeOutput cidadeOutput = modelMapper.map(cidade, CidadeOutput.class);

//        cidadeOutput.add(linkTo(methodOn(CidadeController.class).buscarPeloId(cidadeOutput.getId()))
//                .withSelfRel());

        cidadeModelOutput.add(linkTo(methodOn(CidadeController.class).listar()).withRel("cidades"));

        cidadeModelOutput.getEstado().add(linkTo(methodOn(EstadoController.class).buscarPeloId(cidadeModelOutput.getEstado().getId()))
                .withSelfRel());
        return cidadeModelOutput;
    }

    @Override
    public CollectionModel<CidadeOutput> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
            .add(linkTo(CidadeController.class).withSelfRel());
    }
}
