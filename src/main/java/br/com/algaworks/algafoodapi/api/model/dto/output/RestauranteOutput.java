package br.com.algaworks.algafoodapi.api.model.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteOutput {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaOutput cozinha;
}
