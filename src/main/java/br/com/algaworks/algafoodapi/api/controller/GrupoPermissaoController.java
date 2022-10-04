package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.converter.output.PermissaoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.output.PermissaoOutput;
import br.com.algaworks.algafoodapi.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.model.Grupo;
import br.com.algaworks.algafoodapi.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoOutputConverter permissaoOutputConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<PermissaoOutput> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        CollectionModel<PermissaoOutput> permissoesModel
                = permissaoOutputConverter.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(algaLinks.linkToGrupoPermissoes(grupoId))
                .add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "adicionar"));

        permissoesModel.getContent().forEach(permissaoModel -> {
            permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
                    grupoId, permissaoModel.getId(), "remover"));
        });
        return permissoesModel;
    }

    @Override
    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> adicionar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();

    }
}
