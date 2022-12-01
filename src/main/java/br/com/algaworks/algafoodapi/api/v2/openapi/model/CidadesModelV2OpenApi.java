package br.com.algaworks.algafoodapi.api.v2.openapi.model;

import br.com.algaworks.algafoodapi.api.v2.model.CidadeOutputV2;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class CidadesModelV2OpenApi {

    private CidadesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class CidadesEmbeddedModelOpenApi {

        private List<CidadeOutputV2> cidades;
    }
}
