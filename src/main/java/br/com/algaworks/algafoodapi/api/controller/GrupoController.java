package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.controller.openapi.GrupoControllerOpenApi;
import br.com.algaworks.algafoodapi.api.converter.domain.GrupoDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.GrupoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.GrupoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.GrupoOutput;
import br.com.algaworks.algafoodapi.domain.model.Grupo;
import br.com.algaworks.algafoodapi.domain.repository.GrupoRepository;
import br.com.algaworks.algafoodapi.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoDomainConverter grupoDomainConverter;

    @Autowired
    private GrupoOutputConverter grupoOutputConverter;

    @GetMapping
    public List<GrupoOutput> listar() {
        return grupoOutputConverter.toCollectionGrupoOutput(grupoRepository.findAll());
    }

    @GetMapping("/{id}")
    public GrupoOutput buscarPeloId(@PathVariable Long id) {
        return grupoOutputConverter.toGrupoOutput(grupoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoOutput salvar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoDomainConverter.toDomainObject(grupoInput);
        return grupoOutputConverter.toGrupoOutput(grupoService.salvar(grupo));
    }

    @PutMapping("/{id}")
    public GrupoOutput atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoEncontrado = grupoService.buscarOuFalhar(id);
        grupoDomainConverter.copyToDomainObject(grupoInput, grupoEncontrado);

        return grupoOutputConverter.toGrupoOutput(grupoService.salvar(grupoEncontrado));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        grupoService.excluir(id);
    }
}
