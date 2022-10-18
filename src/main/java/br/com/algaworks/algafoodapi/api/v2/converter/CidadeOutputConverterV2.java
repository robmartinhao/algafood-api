package br.com.algaworks.algafoodapi.api.v2.converter;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CidadeOutput;
import br.com.algaworks.algafoodapi.api.v2.AlgaLinksV2;
import br.com.algaworks.algafoodapi.api.v2.controller.CidadeControllerV2;
import br.com.algaworks.algafoodapi.api.v2.model.CidadeOutputV2;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeOutputConverterV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeOutputV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public CidadeOutputConverterV2() {
        super(CidadeControllerV2.class, CidadeOutputV2.class);
    }

    @Override
    public CidadeOutputV2 toModel(Cidade cidade) {

        CidadeOutputV2 cidadeModelOutput = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModelOutput);

        cidadeModelOutput.add(algaLinks.linkToCidades("cidades"));

        return cidadeModelOutput;
    }

    @Override
    public CollectionModel<CidadeOutputV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
            .add(algaLinks.linkToCidades());
    }
}
