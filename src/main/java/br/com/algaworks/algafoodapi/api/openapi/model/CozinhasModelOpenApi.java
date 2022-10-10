package br.com.algaworks.algafoodapi.api.openapi.model;

import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi {

    private CozinhasModelOpenApi.CozinhaEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public class CozinhaEmbeddedModelOpenApi {
        private List<CozinhaOutput> cozinhas;
    }
}
