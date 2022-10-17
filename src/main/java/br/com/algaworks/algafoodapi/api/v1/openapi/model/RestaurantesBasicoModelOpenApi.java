package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteBasicoOutput;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestaurantesBasicoModel")
@Data
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("RestaurantesEmbeddedModel")
    @Data
    public class RestaurantesEmbeddedModelOpenApi {

        private List<RestauranteBasicoOutput> restaurantes;

    }
}
