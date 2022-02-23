package br.com.algaworks.algafoodapi.api.model.dto.input;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIdInput {

    @NotNull
    private Long id;
}
