package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CidadeOutput;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenApi {

    private CidadeEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public class CidadeEmbeddedModelOpenApi {
        private List<CidadeOutput> cidades;
    }
}
