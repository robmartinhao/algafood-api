package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.FormaPagamentoOutput;
import br.com.algaworks.algafoodapi.api.model.dto.output.PermissaoOutput;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import br.com.algaworks.algafoodapi.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoOutput toPermissaoOutput(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoOutput.class);
    }

    public List<PermissaoOutput> toCollectionFormaPagamentoOutput(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(this::toPermissaoOutput)
                .collect(Collectors.toList());
    }
}
