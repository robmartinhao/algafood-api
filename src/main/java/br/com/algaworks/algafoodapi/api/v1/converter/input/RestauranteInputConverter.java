package br.com.algaworks.algafoodapi.api.v1.converter.input;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.RestauranteInput;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteInput toRestauranteInput(Restaurante restaurante) {
       return modelMapper.map(restaurante, RestauranteInput.class);
    }
}
