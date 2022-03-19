package br.com.algaworks.algafoodapi.api.model.dto.input;

import br.com.algaworks.algafoodapi.core.validation.FileContentType;
import br.com.algaworks.algafoodapi.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @NotNull
    @FileSize(max = "500KB")
    private MultipartFile arquivo;
    @NotBlank
    private String descricao;
}
