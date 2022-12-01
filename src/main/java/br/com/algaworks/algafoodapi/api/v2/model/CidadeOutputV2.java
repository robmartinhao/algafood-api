package br.com.algaworks.algafoodapi.api.v2.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeOutputV2 extends RepresentationModel<CidadeOutputV2> {

    private Long idCidade;
    private String nomeCidade;

    private Long idEstado;
    private String nomeEstado;
}
