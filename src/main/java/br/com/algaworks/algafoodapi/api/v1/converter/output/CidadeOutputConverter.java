package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.CidadeController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CidadeOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CidadeOutputConverter extends RepresentationModelAssemblerSupport<Cidade, CidadeOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CidadeOutputConverter() {
        super(CidadeController.class, CidadeOutput.class);
    }

    @Override
    public CidadeOutput toModel(Cidade cidade) {

        CidadeOutput cidadeModelOutput = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModelOutput);

        if (algaSecurity.podeConsultarCidades()) {
            cidadeModelOutput.add(algaLinks.linkToCidades("cidades"));
        }
        if (algaSecurity.podeConsultarEstados()) {
            cidadeModelOutput.getEstado().add(algaLinks.linkToEstado(cidadeModelOutput.getEstado().getId()));
        }
        return cidadeModelOutput;
    }

    @Override
    public CollectionModel<CidadeOutput> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
            .add(algaLinks.linkToCidades());
    }
}
