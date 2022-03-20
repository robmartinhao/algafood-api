package br.com.algaworks.algafoodapi.api.model.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoOutput {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
