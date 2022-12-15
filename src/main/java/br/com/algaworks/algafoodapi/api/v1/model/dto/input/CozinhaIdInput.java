package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;


@Setter
@Getter
public class CozinhaIdInput {

    @Schema(example = "1")
    @NotNull
    private Long id;
}
