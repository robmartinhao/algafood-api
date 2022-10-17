package br.com.algaworks.algafoodapi.api.v1.converter.domain;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.ProdutoInput;
import br.com.algaworks.algafoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDomainConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoInput produtoInput) {
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto) {
        modelMapper.map(produtoInput, produto);
    }
}
