package br.com.algaworks.algafoodapi.api.v2.model.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Setter
@Getter
public class CozinhaInputV2 {

    @NotBlank
    private String nomeCozinha;
}
