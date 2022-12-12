package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SenhaInput {

    @Schema(example = "123", type = "string")
    @NotBlank
    private String senhaAtual;

    @Schema(example = "123", type = "string")
    @NotBlank
    private String novaSenha;
}
