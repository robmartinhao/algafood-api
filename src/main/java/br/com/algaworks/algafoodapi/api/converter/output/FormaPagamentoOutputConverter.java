package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.controller.FormaPagamentoController;
import br.com.algaworks.algafoodapi.api.model.dto.output.FormaPagamentoOutput;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoOutputConverter extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public FormaPagamentoOutputConverter() {
        super(FormaPagamentoController.class, FormaPagamentoOutput.class);
    }

    @Override
    public FormaPagamentoOutput toModel(FormaPagamento formaPagamento) {
        FormaPagamentoOutput formaPagamentoOutput =
                createModelWithId(formaPagamento.getId(), formaPagamento);

        modelMapper.map(formaPagamento, formaPagamentoOutput);

        formaPagamentoOutput.add(algaLinks.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoOutput;
    }

    @Override
    public CollectionModel<FormaPagamentoOutput> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToFormasPagamento());
    }
}
