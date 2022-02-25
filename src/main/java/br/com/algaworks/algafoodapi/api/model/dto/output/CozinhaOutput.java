package br.com.algaworks.algafoodapi.api.model.dto.output;

import br.com.algaworks.algafoodapi.api.converter.output.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaOutput {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}
