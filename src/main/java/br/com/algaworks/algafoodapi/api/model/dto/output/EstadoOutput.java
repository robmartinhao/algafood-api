package br.com.algaworks.algafoodapi.api.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class EstadoOutput extends RepresentationModel<EstadoOutput> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "São Paulo")
    private String nome;
}
