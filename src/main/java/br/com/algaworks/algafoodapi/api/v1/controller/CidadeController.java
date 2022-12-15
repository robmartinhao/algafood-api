package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.ResourceUriHelper;
import br.com.algaworks.algafoodapi.api.v1.converter.domain.CidadeDomainConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.CidadeOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.CidadeInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CidadeOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.CidadeControllerOpenApi;
import br.com.algaworks.algafoodapi.core.security.CheckSecurity;
import br.com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import br.com.algaworks.algafoodapi.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeOutputConverter cidadeOutputConverter;

    @Autowired
    private CidadeDomainConverter cidadeDomainConverter;

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeOutput> listar() {
        return cidadeOutputConverter.toCollectionModel(cidadeRepository.findAll());
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeOutput buscarPeloId(@PathVariable Long id) {
        return cidadeOutputConverter.toModel(cidadeService.buscarOuFalhar(id));
    }

    @CheckSecurity.Cidades.PodeEditar
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutput salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeDomainConverter.toDomainObject(cidadeInput);
            CidadeOutput cidadeOutput = cidadeOutputConverter.toModel(cidadeService.salvar(cidade));

            ResourceUriHelper.AddUriInResponseHeader(cidadeOutput.getId());

            return cidadeOutput;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeOutput atualizar(@PathVariable Long id,
                                  @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeEncontrada = cidadeService.buscarOuFalhar(id);

            cidadeDomainConverter.copyToDomainObject(cidadeInput, cidadeEncontrada);
            //BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");
            return cidadeOutputConverter.toModel(cidadeService.salvar(cidadeEncontrada));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        cidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
