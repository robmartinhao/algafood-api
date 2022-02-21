package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.UsuarioOutput;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioOutput toUsuarioOutput(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioOutput.class);
    }

    public List<UsuarioOutput> toCollectionUsuarioOutput(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toUsuarioOutput)
                .collect(Collectors.toList());
    }
}
