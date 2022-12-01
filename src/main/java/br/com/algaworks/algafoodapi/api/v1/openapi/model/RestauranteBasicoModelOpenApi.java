package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CozinhaOutput;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteBasicoModelOpenApi {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaOutput cozinha;
}
