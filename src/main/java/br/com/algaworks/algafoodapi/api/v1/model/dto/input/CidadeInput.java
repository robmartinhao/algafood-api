package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @ApiModelProperty(example = "São Caetano")
    @NotBlank
    private String nome;
    @Valid
    @NotNull
    private EstadoIdInput estado;
}
