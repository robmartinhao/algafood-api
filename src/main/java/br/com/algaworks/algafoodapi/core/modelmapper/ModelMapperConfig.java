package br.com.algaworks.algafoodapi.core.modelmapper;

import br.com.algaworks.algafoodapi.api.model.dto.output.RestauranteOutput;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurante.class, RestauranteOutput.class)
            .addMapping(Restaurante::getTaxaFrete, RestauranteOutput::setPrecoFrete);

        return modelMapper;
    }
}
