package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.ProdutoOutput;
import br.com.algaworks.algafoodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoOutput toProdutoOutput(Produto produto) {
        return modelMapper.map(produto, ProdutoOutput.class);
    }

    public List<ProdutoOutput> toCollectionProdutoOutput(Collection<Produto> produtos) {
        return produtos.stream()
                .map(this::toProdutoOutput)
                .collect(Collectors.toList());
    }
}
