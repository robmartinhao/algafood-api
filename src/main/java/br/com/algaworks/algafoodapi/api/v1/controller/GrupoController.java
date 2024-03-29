package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.domain.GrupoDomainConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.GrupoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.GrupoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.GrupoControllerOpenApi;
import br.com.algaworks.algafoodapi.core.security.CheckSecurity;
import br.com.algaworks.algafoodapi.domain.model.Grupo;
import br.com.algaworks.algafoodapi.domain.repository.GrupoRepository;
import br.com.algaworks.algafoodapi.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoDomainConverter grupoDomainConverter;

    @Autowired
    private GrupoOutputConverter grupoOutputConverter;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GrupoOutput> listar() {
        return grupoOutputConverter.toCollectionModel(grupoRepository.findAll());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(path ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoOutput buscarPeloId(@PathVariable Long id) {
        return grupoOutputConverter.toModel(grupoService.buscarOuFalhar(id));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoOutput salvar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoDomainConverter.toDomainObject(grupoInput);
        return grupoOutputConverter.toModel(grupoService.salvar(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping(path ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoOutput atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoEncontrado = grupoService.buscarOuFalhar(id);
        grupoDomainConverter.copyToDomainObject(grupoInput, grupoEncontrado);

        return grupoOutputConverter.toModel(grupoService.salvar(grupoEncontrado));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        grupoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
