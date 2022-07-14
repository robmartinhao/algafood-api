package br.com.algaworks.algafoodapi.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;
@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    private Integer status;

    @ApiModelProperty(example = "https://algafood.com.br//mesangem-incompreensivel", position = 5)
    private String type;

    @ApiModelProperty(example = "Mensagem incompreensível", position = 10)
    private String title;

    @ApiModelProperty(example = "O corpo da requisição está inválido. Verifique erro de sintaxe", position = 15)
    private String detail;

    @ApiModelProperty(example = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.", position = 20)
    private String userMessage;

    @ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 25)
    private OffsetDateTime timestamp;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 30)
    private List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "preco")
        private String name;

        @ApiModelProperty(example = "O preço é obrigatório")
        private String userMessage;
    }
}
