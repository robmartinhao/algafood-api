package br.com.algaworks.algafoodapi.api.v1.converter.output;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.controller.FormaPagamentoController;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FormaPagamentoOutput;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
