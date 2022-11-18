package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.GrupoController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class GrupoOutputConverter extends RepresentationModelAssemblerSupport<Grupo, GrupoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public GrupoOutputConverter() {
        super(GrupoController.class, GrupoOutput.class);
    }

    @Override
    public GrupoOutput toModel(Grupo grupo) {
        GrupoOutput grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoModel.add(algaLinks.linkToGrupos("grupos"));
            grupoModel.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }
        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoOutput> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoOutput> collectionModel = super.toCollectionModel(entities);
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(algaLinks.linkToGrupos());
        }
        return super.toCollectionModel(entities);
    }
}
