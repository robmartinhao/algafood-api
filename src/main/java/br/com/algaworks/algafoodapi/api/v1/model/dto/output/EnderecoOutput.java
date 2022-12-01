package br.com.algaworks.algafoodapi.api.v1.model.dto.output;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoOutput {

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeResumoOutput cidade;
}
