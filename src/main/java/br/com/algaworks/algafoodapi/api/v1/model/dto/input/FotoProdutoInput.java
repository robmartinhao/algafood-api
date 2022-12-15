package br.com.algaworks.algafoodapi.api.v1.model.dto.input;

import br.com.algaworks.algafoodapi.core.validation.FileContentType;
import br.com.algaworks.algafoodapi.core.validation.FileSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @Schema(description = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)")
    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    private MultipartFile arquivo;

    @Schema(description = "Descrição da foto do produto")
    @NotBlank
    private String descricao;
}
