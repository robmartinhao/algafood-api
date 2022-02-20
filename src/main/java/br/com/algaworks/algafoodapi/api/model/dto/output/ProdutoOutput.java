package br.com.algaworks.algafoodapi.api.model.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoOutput {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean ativo;
}
