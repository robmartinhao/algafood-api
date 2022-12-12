package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "fotos")
@Getter
@Setter
public class FotoProdutoOutput extends RepresentationModel<FotoProdutoOutput> {

    @Schema(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
    private String nomeArquivo;

    @Schema(example = "Prime Rib ao ponto")
    private String descricao;

    @Schema(example = "image/jpeg")
    private String contentType;

    @Schema(example = "202912")
    private Long tamanho;
}
