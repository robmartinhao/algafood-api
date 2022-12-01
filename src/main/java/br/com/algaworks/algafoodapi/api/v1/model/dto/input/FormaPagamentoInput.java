package br.com.algaworks.algafoodapi.api.v1.model.dto.input;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoInput {

    @NotBlank
    private String descricao;
}
