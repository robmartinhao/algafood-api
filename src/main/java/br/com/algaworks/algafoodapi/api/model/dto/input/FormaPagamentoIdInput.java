package br.com.algaworks.algafoodapi.api.model.dto.input;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIdInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;
}
