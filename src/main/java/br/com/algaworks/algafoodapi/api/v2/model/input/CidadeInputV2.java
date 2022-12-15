package br.com.algaworks.algafoodapi.api.v2.model.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInputV2 {

    @NotBlank
    private String nomeCidade;

    @NotNull
    private Long idEstado;
}
