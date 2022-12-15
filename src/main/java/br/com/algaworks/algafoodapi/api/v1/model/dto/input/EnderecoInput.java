package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoInput {

    @Schema(example = "38400-000")
    @NotBlank
    private String cep;

    @Schema(example = "Rua Floriano Peixoto")
    @NotBlank
    private String logradouro;

    @Schema(example = "\"1500\"")
    @NotBlank
    private String numero;

    @Schema(example = "Apto 901")
    private String complemento;

    @Schema(example = "Centro")
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CozinhaIdInput cidade;
}
