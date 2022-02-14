package br.com.algaworks.algafoodapi.api.converter;

import br.com.algaworks.algafoodapi.api.model.dto.input.CozinhaIdInput;
import br.com.algaworks.algafoodapi.api.model.dto.input.RestauranteInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.api.model.dto.output.RestauranteOutput;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteOutputConverter {

    public RestauranteOutput toRestauranteOutput(Restaurante restaurante) {
        CozinhaOutput cozinhaOutput = new CozinhaOutput();
        cozinhaOutput.setId(restaurante.getCozinha().getId());
        cozinhaOutput.setNome(restaurante.getCozinha().getNome());

        RestauranteOutput restauranteOutput = new RestauranteOutput();
        restauranteOutput.setId(restaurante.getId());
        restauranteOutput.setNome(restaurante.getNome());
        restauranteOutput.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteOutput.setCozinha(cozinhaOutput);

        return restauranteOutput;
    }

    public List<RestauranteOutput> toCollectionRestauranteOutput(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toRestauranteOutput(restaurante))
                .collect(Collectors.toList());
    }
}
