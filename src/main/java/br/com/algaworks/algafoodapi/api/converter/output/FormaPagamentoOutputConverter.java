package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.model.dto.output.FormaPagamentoOutput;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoOutputConverter {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoOutput toFormaPagamentoOutput(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoOutput.class);
    }

    public List<FormaPagamentoOutput> toCollectionFormaPagamentoOutput(Collection<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
                .map(this::toFormaPagamentoOutput)
                .collect(Collectors.toList());
    }
}
