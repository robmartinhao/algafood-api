package br.com.algaworks.algafoodapi.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Links")
public class LinksModelApi {

    private LinkModel rel;

    @Getter
    @Setter
    @ApiModel("Link")
    private class LinkModel {
        private String rel;
        private boolean templated;
    }
}
