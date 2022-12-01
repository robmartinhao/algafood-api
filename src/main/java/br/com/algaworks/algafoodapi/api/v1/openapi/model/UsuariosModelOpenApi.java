package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.UsuarioOutput;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class UsuariosModelOpenApi {

    private UsuariosEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class UsuariosEmbeddedModelOpenApi {

        private List<UsuarioOutput> usuarios;
    }
}
