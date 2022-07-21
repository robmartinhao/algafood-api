package br.com.algaworks.algafoodapi.api.model.dto.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeOutput extends RepresentationModel<CidadeOutput> {

//    @ApiModelProperty(value = "ID da cidade", example = "1")
    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "São Caetano")
    private String nome;
    private EstadoOutput estado;
}
