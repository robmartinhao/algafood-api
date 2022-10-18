package br.com.algaworks.algafoodapi.api.v2.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.EstadoOutput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeOutputV2 extends RepresentationModel<CidadeOutputV2> {

    @ApiModelProperty(example = "1")
    private Long idCidade;
    @ApiModelProperty(example = "São Caetano")
    private String nomeCidade;

    private Long idEstado;
    private String nomeEstado;
}
