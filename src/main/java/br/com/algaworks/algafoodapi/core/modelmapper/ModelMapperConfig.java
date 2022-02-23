package br.com.algaworks.algafoodapi.core.modelmapper;

import br.com.algaworks.algafoodapi.api.model.dto.input.ItemPedidoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.EnderecoOutput;
import br.com.algaworks.algafoodapi.domain.model.Endereco;
import br.com.algaworks.algafoodapi.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        var enderecoToEnderecoOutputTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoOutput.class);
        enderecoToEnderecoOutputTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoOutputdest, enderecoSrcValue) -> enderecoOutputdest.getCidade().setEstado(enderecoSrcValue)
        );

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }
}
