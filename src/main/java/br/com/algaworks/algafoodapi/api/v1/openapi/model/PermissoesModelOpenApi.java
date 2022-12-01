package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PermissaoOutput;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class PermissoesModelOpenApi {

    private PermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class PermissoesEmbeddedModelOpenApi {

        private List<PermissaoOutput> permissoes;
    }
}
