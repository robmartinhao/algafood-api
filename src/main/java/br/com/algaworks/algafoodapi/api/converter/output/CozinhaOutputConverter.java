package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.controller.CozinhaController;
import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CozinhaOutputConverter extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutput> {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaOutputConverter() {
        super(CozinhaController.class, CozinhaOutput.class);
    }

    public CozinhaOutput toModel(Cozinha cozinha) {
        CozinhaOutput cozinhaModelOutput = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModelOutput);

        cozinhaModelOutput.add(linkTo(CozinhaController.class).withRel("cozinhas"));

        return cozinhaModelOutput;
    }
}
