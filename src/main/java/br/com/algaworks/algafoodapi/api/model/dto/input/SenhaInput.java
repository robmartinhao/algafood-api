package br.com.algaworks.algafoodapi.api.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SenhaInput {

    @NotBlank
    private String senhaAtual;
    @NotBlank
    private String novaSenha;
}
