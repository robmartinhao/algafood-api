package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioInput {

    @Schema(example = "João da Silva")
    @NotBlank
    private String nome;

    @Schema(example = "joao.ger@algafood.com.br")
    @NotBlank
    @Email
    private String email;
}