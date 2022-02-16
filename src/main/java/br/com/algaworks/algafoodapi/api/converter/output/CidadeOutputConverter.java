package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.CidadeOutput;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeOutput toCidadeOutput(Cidade cidade) {
        return modelMapper.map(cidade, CidadeOutput.class);
    }

    public List<CidadeOutput> toCollectionCidadeOutput(List<Cidade> cidades) {
        return cidades.stream()
                .map(this::toCidadeOutput)
                .collect(Collectors.toList());
    }
}
