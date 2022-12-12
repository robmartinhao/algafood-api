package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @Schema(example = "Espetinho de Cupim")
    @NotBlank
    private String nome;

    @Schema(example = "Acompanha farinha, mandioca e vinagrete")
    @NotBlank
    private String descricao;

    @Schema(example = "12.50")
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @Schema(example = "true")
    @NotNull
    private Boolean ativo;
}
