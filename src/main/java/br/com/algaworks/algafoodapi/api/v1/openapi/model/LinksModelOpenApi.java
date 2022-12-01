package br.com.algaworks.algafoodapi.api.v1.openapi.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinksModelOpenApi {

    private LinkModel rel;

    @Getter
    @Setter
    private class LinkModel {
        private String rel;
        private boolean templated;
    }
}
