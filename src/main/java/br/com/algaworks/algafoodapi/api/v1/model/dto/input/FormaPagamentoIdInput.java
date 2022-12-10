package br.com.algaworks.algafoodapi.api.v1.model.dto.input;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIdInput {

    @Schema(example = "1")
    @NotNull
    private Long id;
}
