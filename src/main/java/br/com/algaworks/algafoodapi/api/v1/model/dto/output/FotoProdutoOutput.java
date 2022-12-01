package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "fotos")
@Getter
@Setter
public class FotoProdutoOutput extends RepresentationModel<FotoProdutoOutput> {

    private String nomeArquivo;

    private String descricao;

    private String contentType;

    private Long tamanho;
}
