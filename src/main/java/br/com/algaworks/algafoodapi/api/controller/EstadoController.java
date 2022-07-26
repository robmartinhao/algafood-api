package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.EstadoDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.EstadoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.EstadoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.EstadoOutput;
import br.com.algaworks.algafoodapi.api.openapi.controller.EstadoControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import br.com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import br.com.algaworks.algafoodapi.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoOutputConverter estadoOutputConverter;

    @Autowired
    private EstadoDomainConverter estadoDomainConverter;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EstadoOutput> listar() {
        return estadoOutputConverter.toCollectionModel(estadoRepository.findAll());
    }

    @GetMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoOutput buscarPeloId(@PathVariable Long id) {
        return estadoOutputConverter.toModel(estadoService.buscarOuFalhar(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoOutput salvar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoDomainConverter.toDomainObject(estadoInput);
        return estadoOutputConverter.toModel(estadoService.salvar(estado));
    }

    @PutMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoOutput atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoEncontrado = estadoService.buscarOuFalhar(id);
        estadoDomainConverter.copyToDomainObject(estadoInput, estadoEncontrado);

        return estadoOutputConverter.toModel(estadoService.salvar(estadoEncontrado));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        estadoService.excluir(id);
    }
}
