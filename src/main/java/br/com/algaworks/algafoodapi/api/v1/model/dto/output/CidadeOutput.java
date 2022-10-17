package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeOutput extends RepresentationModel<CidadeOutput> {

//    @ApiModelProperty(value = "ID da cidade", example = "1")
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "São Caetano")
    private String nome;
    private EstadoOutput estado;
}
