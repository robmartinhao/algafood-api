package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class EstadoInput {

    @NotBlank
    private String nome;
}
