package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.CozinhaController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public CozinhaOutputConverter() {
        super(CozinhaController.class, CozinhaOutput.class);
    }

    public CozinhaOutput toModel(Cozinha cozinha) {
        CozinhaOutput cozinhaModelOutput = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModelOutput);

        if (algaSecurity.podeConsultarCozinhas()) {
            cozinhaModelOutput.add(algaLinks.linkToCozinhas("cozinhas"));
        }
        return cozinhaModelOutput;
    }
}
