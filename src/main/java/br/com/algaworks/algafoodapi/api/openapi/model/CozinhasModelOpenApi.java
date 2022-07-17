package br.com.algaworks.algafoodapi.api.openapi.model;

import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaOutput> {

}
