package br.com.algaworks.algafoodapi.api.converter;

import br.com.algaworks.algafoodapi.api.model.dto.input.CozinhaIdInput;
import br.com.algaworks.algafoodapi.api.model.dto.input.RestauranteInput;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputConverter {

    public RestauranteInput toRestauranteInput(Restaurante restaurante) {
        RestauranteInput restauranteInput = new RestauranteInput();
        restauranteInput.setNome(restaurante.getNome());
        restauranteInput.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaIdInput cozinhaIdInput = new CozinhaIdInput();
        cozinhaIdInput.setId(restaurante.getCozinha().getId());

        restauranteInput.setCozinha(cozinhaIdInput);

        return restauranteInput;
    }
}
