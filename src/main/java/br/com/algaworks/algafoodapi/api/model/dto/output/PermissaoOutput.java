package br.com.algaworks.algafoodapi.api.model.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoOutput {

    private Long id;
    private String nome;
    private String descricao;
}
