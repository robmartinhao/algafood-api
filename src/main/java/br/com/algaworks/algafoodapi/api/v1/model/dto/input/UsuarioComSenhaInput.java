package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioComSenhaInput extends UsuarioInput {

    @NotBlank
    private String senha;
}
