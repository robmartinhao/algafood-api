package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.controller.CozinhaController;
import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaOutputConverter extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public CozinhaOutputConverter() {
        super(CozinhaController.class, CozinhaOutput.class);
    }

    public CozinhaOutput toModel(Cozinha cozinha) {
        CozinhaOutput cozinhaModelOutput = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModelOutput);

        cozinhaModelOutput.add(algaLinks.linkToCozinhas("cozinhas"));

        return cozinhaModelOutput;
    }
}
