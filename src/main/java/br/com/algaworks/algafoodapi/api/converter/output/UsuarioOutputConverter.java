package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.controller.UsuarioController;
import br.com.algaworks.algafoodapi.api.model.dto.output.UsuarioOutput;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UsuarioOutputConverter extends RepresentationModelAssemblerSupport<Usuario, UsuarioOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public UsuarioOutputConverter() {
        super(UsuarioController.class, UsuarioOutput.class);
    }

    @Override
    public UsuarioOutput toModel(Usuario usuario) {
        UsuarioOutput usuarioModelOutput = createModelWithId(usuario.getId(), usuario);

        modelMapper.map(usuario, usuarioModelOutput);

        usuarioModelOutput.add(algaLinks.linkToUsuarios("usuarios"));

        usuarioModelOutput.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuarios"));

        return usuarioModelOutput;
    }

    @Override
    public CollectionModel<UsuarioOutput> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }
}
