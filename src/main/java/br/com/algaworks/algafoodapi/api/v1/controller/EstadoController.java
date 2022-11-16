package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.domain.EstadoDomainConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.EstadoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.EstadoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.EstadoOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.EstadoControllerOpenApi;
import br.com.algaworks.algafoodapi.core.security.CheckSecurity;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import br.com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import br.com.algaworks.algafoodapi.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController implements EstadoControllerOpenApi {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoOutputConverter estadoOutputConverter;

    @Autowired
    private EstadoDomainConverter estadoDomainConverter;

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EstadoOutput> listar() {
        return estadoOutputConverter.toCollectionModel(estadoRepository.findAll());
    }

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoOutput buscarPeloId(@PathVariable Long id) {
        return estadoOutputConverter.toModel(estadoService.buscarOuFalhar(id));
    }

    @CheckSecurity.Estados.PodeEditar
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoOutput salvar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoDomainConverter.toDomainObject(estadoInput);
        return estadoOutputConverter.toModel(estadoService.salvar(estado));
    }

    @CheckSecurity.Estados.PodeEditar
    @PutMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoOutput atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoEncontrado = estadoService.buscarOuFalhar(id);
        estadoDomainConverter.copyToDomainObject(estadoInput, estadoEncontrado);

        return estadoOutputConverter.toModel(estadoService.salvar(estadoEncontrado));
    }

    @CheckSecurity.Estados.PodeEditar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        estadoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
