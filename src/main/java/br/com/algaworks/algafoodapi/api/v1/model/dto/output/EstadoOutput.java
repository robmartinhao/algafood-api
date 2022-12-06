package br.com.algaworks.algafoodapi.api.v1.model.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Getter
@Setter
public class EstadoOutput extends RepresentationModel<EstadoOutput> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Minas Gerais")
    private String nome;
}
