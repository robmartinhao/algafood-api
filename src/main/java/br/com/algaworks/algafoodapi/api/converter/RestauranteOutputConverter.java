package br.com.algaworks.algafoodapi.api.converter;

import br.com.algaworks.algafoodapi.api.model.dto.output.RestauranteOutput;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteOutput toRestauranteOutput(Restaurante restaurante) {
        return  modelMapper.map(restaurante, RestauranteOutput.class);
    }

    public List<RestauranteOutput> toCollectionRestauranteOutput(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toRestauranteOutput(restaurante))
                .collect(Collectors.toList());
    }
}
