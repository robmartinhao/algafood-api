package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class GruposModelOpenApi {

    private GruposEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class GruposEmbeddedModelOpenApi {

        private List<GrupoOutput> grupos;
    }
}
