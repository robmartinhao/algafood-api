package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.api.model.dto.output.RestauranteOutput;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaOutput toCozinhaOutput(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaOutput.class);
    }

    public List<CozinhaOutput> toCollectionCozinhaOutput(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(this::toCozinhaOutput)
                .collect(Collectors.toList());
    }
}
