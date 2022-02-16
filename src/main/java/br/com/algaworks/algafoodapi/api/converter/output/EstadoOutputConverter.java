package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.api.model.dto.output.EstadoOutput;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoOutput toEstadoOutput(Estado estado) {
        return modelMapper.map(estado, EstadoOutput.class);
    }

    public List<EstadoOutput> toCollectionEstadoOutput(List<Estado> estados) {
        return estados.stream()
                .map(this::toEstadoOutput)
                .collect(Collectors.toList());
    }
}
