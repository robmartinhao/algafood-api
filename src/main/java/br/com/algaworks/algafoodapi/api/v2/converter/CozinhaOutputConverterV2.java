package br.com.algaworks.algafoodapi.api.v2.converter;

import br.com.algaworks.algafoodapi.api.v1.controller.CozinhaController;
import br.com.algaworks.algafoodapi.api.v2.AlgaLinksV2;
import br.com.algaworks.algafoodapi.api.v2.model.CozinhaOutputV2;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaOutputConverterV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaOutputV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public CozinhaOutputConverterV2() {
        super(CozinhaController.class, CozinhaOutputV2.class);
    }

    public CozinhaOutputV2 toModel(Cozinha cozinha) {
        CozinhaOutputV2 cozinhaModelOutput = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModelOutput);

        cozinhaModelOutput.add(algaLinks.linkToCozinhas("cozinhas"));

        return cozinhaModelOutput;
    }
}
