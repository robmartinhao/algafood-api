package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteOutput extends RepresentationModel<RestauranteOutput> {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaOutput cozinha;

    private Boolean ativo;
    private Boolean aberto;
    private EnderecoOutput endereco;
}
