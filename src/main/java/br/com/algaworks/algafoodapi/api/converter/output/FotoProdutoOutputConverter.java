package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.FotoProdutoOutput;
import br.com.algaworks.algafoodapi.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoOutput toFotoProdutoOutput(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoOutput.class);
    }

}
