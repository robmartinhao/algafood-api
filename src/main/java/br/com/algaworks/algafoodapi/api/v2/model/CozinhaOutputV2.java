package br.com.algaworks.algafoodapi.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@ApiModel("CozinhaOutput")
@Getter
@Setter
public class CozinhaOutputV2 extends RepresentationModel<CozinhaOutputV2> {

    @ApiModelProperty(example = "1")
    private Long idCozinha;

    @ApiModelProperty(example = "Brasileira")
    private String nomeCozinha;
}
