package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.controller.GrupoController;
import br.com.algaworks.algafoodapi.api.model.dto.output.GrupoOutput;
import br.com.algaworks.algafoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoOutputConverter extends RepresentationModelAssemblerSupport<Grupo, GrupoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public GrupoOutputConverter() {
        super(GrupoController.class, GrupoOutput.class);
    }

    @Override
    public GrupoOutput toModel(Grupo grupo) {
        GrupoOutput grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);

        grupoModel.add(algaLinks.linkToGrupos("grupos"));
        grupoModel.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoOutput> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToGrupos());
    }
}
