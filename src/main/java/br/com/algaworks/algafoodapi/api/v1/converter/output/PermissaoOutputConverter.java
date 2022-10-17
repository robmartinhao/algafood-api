package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PermissaoOutput;
import br.com.algaworks.algafoodapi.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissaoOutputConverter implements RepresentationModelAssembler<Permissao, PermissaoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PermissaoOutput toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoOutput.class);
    }

    public CollectionModel<PermissaoOutput> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissoes());
    }
}
