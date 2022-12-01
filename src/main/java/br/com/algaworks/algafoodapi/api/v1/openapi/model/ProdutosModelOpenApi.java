package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import br.com.algaworks.algafoodapi.api.v1.model.dto.output.ProdutoOutput;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class ProdutosModelOpenApi {

    private ProdutosEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    public class ProdutosEmbeddedModelOpenApi {

        private List<ProdutoOutput> produtos;
    }
}