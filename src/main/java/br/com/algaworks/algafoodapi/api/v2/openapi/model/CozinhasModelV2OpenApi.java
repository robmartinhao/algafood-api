package br.com.algaworks.algafoodapi.api.v2.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CozinhaOutput;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Setter
@Getter
public class CozinhasModelV2OpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelV2OpenApi page;

    @Data
    public class CozinhasEmbeddedModelOpenApi {

        private List<CozinhaOutput> cozinhas;
    }
}
