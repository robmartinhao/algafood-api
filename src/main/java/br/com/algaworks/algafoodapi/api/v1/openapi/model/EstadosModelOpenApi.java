package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.EstadoOutput;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class EstadosModelOpenApi {

    private EstadosEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class EstadosEmbeddedModelOpenApi {

        private List<EstadoOutput> estados;
    }
}
