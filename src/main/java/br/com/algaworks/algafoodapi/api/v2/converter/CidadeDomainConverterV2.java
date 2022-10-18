package br.com.algaworks.algafoodapi.api.v2.converter;

import br.com.algaworks.algafoodapi.api.v2.model.input.CidadeInputV2;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDomainConverterV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInputV2 cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInputV2 cidadeInput, Cidade cidade) {
        // Para evitar: org.hibernate.HibernateException: identifier of an instance of
        // br.com.algaworks.algafoodapi.domain.model.Cozinha was altered from 1 to 2
        cidade.setEstado(new Estado());
        modelMapper.map(cidadeInput, cidade);
    }
}
