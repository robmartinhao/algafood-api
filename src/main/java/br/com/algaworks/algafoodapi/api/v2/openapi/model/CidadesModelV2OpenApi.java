package br.com.algaworks.algafoodapi.api.v2.openapi.model;

import br.com.algaworks.algafoodapi.api.v2.model.CidadeOutputV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelV2OpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadesEmbeddedModelOpenApi {

        private List<CidadeOutputV2> cidades;
    }
}
