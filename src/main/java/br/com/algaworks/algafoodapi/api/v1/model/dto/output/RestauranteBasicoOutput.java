package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteBasicoOutput extends RepresentationModel<RestauranteBasicoOutput> {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaOutput cozinha;
}
