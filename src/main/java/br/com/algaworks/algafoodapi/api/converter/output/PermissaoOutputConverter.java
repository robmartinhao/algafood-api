package br.com.algaworks.algafoodapi.api.converter.output;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.model.dto.output.FormaPagamentoOutput;
import br.com.algaworks.algafoodapi.api.model.dto.output.PermissaoOutput;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import br.com.algaworks.algafoodapi.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoOutputConverter implements RepresentationModelAssembler<Permissao, PermissaoOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PermissaoOutput toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoOutput.class);
    }

    public CollectionModel<PermissaoOutput> toCollectionModel(Iterable<? extends Permissao> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissoes());
    }
}
