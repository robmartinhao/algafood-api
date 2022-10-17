package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
@Getter
@Setter
public class GrupoOutput extends RepresentationModel<GrupoOutput> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Gerente")
    private String nome;
}
