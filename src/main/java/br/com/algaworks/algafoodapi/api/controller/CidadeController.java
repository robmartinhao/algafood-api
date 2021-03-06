package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.ResourceUriHelper;
import br.com.algaworks.algafoodapi.api.converter.domain.CidadeDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.CidadeOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.CidadeInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.CidadeOutput;
import br.com.algaworks.algafoodapi.api.openapi.controller.CidadeControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import br.com.algaworks.algafoodapi.domain.service.CidadeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeOutputConverter cidadeOutputConverter;

    @Autowired
    private CidadeDomainConverter cidadeDomainConverter;

    @ApiOperation("Lista as cidades")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CidadeOutput> listar() {
        return cidadeOutputConverter.toCollectionCidadeOutput(cidadeRepository.findAll());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeOutput buscarPeloId(@PathVariable Long id) {

        CidadeOutput cidadeOutput = cidadeOutputConverter.toCidadeOutput(cidadeService.buscarOuFalhar(id));

        cidadeOutput.add(linkTo(methodOn(CidadeController.class).buscarPeloId(cidadeOutput.getId()))
                .withSelfRel());

        cidadeOutput.add(linkTo(methodOn(CidadeController.class).listar()).withRel("cidades"));

        cidadeOutput.getEstado().add(linkTo(methodOn(EstadoController.class).buscarPeloId(cidadeOutput.getEstado().getId()))
                .withSelfRel());

        return cidadeOutput;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutput salvar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeDomainConverter.toDomainObject(cidadeInput);
            CidadeOutput cidadeOutput = cidadeOutputConverter.toCidadeOutput(cidadeService.salvar(cidade));

            ResourceUriHelper.AddUriInResponseHeader(cidadeOutput.getId());

            return cidadeOutput;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CidadeOutput atualizar(@PathVariable Long id,
                                  @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeEncontrada = cidadeService.buscarOuFalhar(id);

            cidadeDomainConverter.copyToDomainObject(cidadeInput, cidadeEncontrada);
            //BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");
            return cidadeOutputConverter.toCidadeOutput(cidadeService.salvar(cidadeEncontrada));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cidadeService.excluir(id);
    }
}
