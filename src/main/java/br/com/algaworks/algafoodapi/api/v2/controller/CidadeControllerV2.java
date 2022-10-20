package br.com.algaworks.algafoodapi.api.v2.controller;

import br.com.algaworks.algafoodapi.api.ResourceUriHelper;
import br.com.algaworks.algafoodapi.api.v2.converter.CidadeDomainConverterV2;
import br.com.algaworks.algafoodapi.api.v2.converter.CidadeOutputConverterV2;
import br.com.algaworks.algafoodapi.api.v2.model.CidadeOutputV2;
import br.com.algaworks.algafoodapi.api.v2.model.input.CidadeInputV2;
import br.com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import br.com.algaworks.algafoodapi.domain.service.CidadeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeOutputConverterV2 cidadeOutputConverter;

    @Autowired
    private CidadeDomainConverterV2 cidadeDomainConverter;

    @ApiOperation("Lista as cidades")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeOutputV2> listar() {
        return cidadeOutputConverter.toCollectionModel(cidadeRepository.findAll());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeOutputV2 buscarPeloId(@PathVariable Long id) {
        return cidadeOutputConverter.toModel(cidadeService.buscarOuFalhar(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutputV2 salvar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeDomainConverter.toDomainObject(cidadeInput);
            CidadeOutputV2 cidadeOutput = cidadeOutputConverter.toModel(cidadeService.salvar(cidade));

            ResourceUriHelper.AddUriInResponseHeader(cidadeOutput.getIdCidade());

            return cidadeOutput;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeOutputV2 atualizar(@PathVariable Long id,
                                  @RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidadeEncontrada = cidadeService.buscarOuFalhar(id);

            cidadeDomainConverter.copyToDomainObject(cidadeInput, cidadeEncontrada);
            //BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");
            return cidadeOutputConverter.toModel(cidadeService.salvar(cidadeEncontrada));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
