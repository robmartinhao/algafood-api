package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoOutput extends RepresentationModel<ProdutoOutput> {

    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;
}
