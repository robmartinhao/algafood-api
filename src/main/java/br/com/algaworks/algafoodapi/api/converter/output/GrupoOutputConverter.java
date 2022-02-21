package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.GrupoOutput;
import br.com.algaworks.algafoodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoOutput toGrupoOutput(Grupo grupo) {
        return modelMapper.map(grupo, GrupoOutput.class);
    }

    public List<GrupoOutput> toCollectionGrupoOutput(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(this::toGrupoOutput)
                .collect(Collectors.toList());
    }
}
