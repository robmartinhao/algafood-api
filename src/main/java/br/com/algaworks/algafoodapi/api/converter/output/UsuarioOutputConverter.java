package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.controller.UsuarioController;
import br.com.algaworks.algafoodapi.api.controller.UsuarioGrupoController;
import br.com.algaworks.algafoodapi.api.model.dto.output.UsuarioOutput;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioOutputConverter extends RepresentationModelAssemblerSupport<Usuario, UsuarioOutput> {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioOutputConverter() {
        super(UsuarioController.class, UsuarioOutput.class);
    }

    @Override
    public UsuarioOutput toModel(Usuario usuario) {
        UsuarioOutput usuarioModelOutput = createModelWithId(usuario.getId(), usuario);

        modelMapper.map(usuario, usuarioModelOutput);

        usuarioModelOutput.add(linkTo(methodOn(UsuarioController.class).listar())
                .withRel("usuarios"));

        usuarioModelOutput.add(linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuario.getId())).withRel("usuarios"));

        return usuarioModelOutput;
    }

    @Override
    public CollectionModel<UsuarioOutput> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }
}
