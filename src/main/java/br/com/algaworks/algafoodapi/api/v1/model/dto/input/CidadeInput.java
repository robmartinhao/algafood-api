package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeInput {

    @Schema(example = "Uberlândia"/*, required = true*/)
    @NotBlank
    private String nome;


//    @Schema(required = true)
    @Valid
    @NotNull
    private EstadoIdInput estado;
}
