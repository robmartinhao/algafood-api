package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SenhaInput {

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String senhaAtual;

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String novaSenha;
}
