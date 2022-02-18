package br.com.algaworks.algafoodapi.api.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioInput {

    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
}