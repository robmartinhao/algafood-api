package br.com.algaworks.algafoodapi.api.model.dto.output;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoOutput {

    @ApiModelProperty(example = "38400-000")
    private String cep;

    @ApiModelProperty(example = "Rua Floriano Peixoto")
    private String logradouro;

    @ApiModelProperty(example = "1500")
    private String numero;

    @ApiModelProperty(example = "Apto 901")
    private String complemento;

    @ApiModelProperty(example = "Centro")
    private String bairro;

    private CidadeResumoOutput cidade;
}
