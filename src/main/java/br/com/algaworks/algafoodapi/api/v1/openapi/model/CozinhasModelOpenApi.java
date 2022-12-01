package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CozinhaOutput;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
public class CozinhasModelOpenApi {

    private CozinhasModelOpenApi.CozinhaEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Data
    public class CozinhaEmbeddedModelOpenApi {
        private List<CozinhaOutput> cozinhas;
    }
}
