package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.AlgaLinks;
import br.com.algaworks.algafoodapi.api.v1.converter.output.GrupoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.GrupoOutput;
import br.com.algaworks.algafoodapi.core.security.AlgaSecurity;
import br.com.algaworks.algafoodapi.core.security.CheckSecurity;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import br.com.algaworks.algafoodapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoOutputConverter grupoOutputConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GrupoOutput> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        CollectionModel<GrupoOutput> gruposModel = grupoOutputConverter.toCollectionModel(usuario.getGrupos())
                .removeLinks();

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposModel.add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

            gruposModel.getContent().forEach(grupoModel -> {
                grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
                        usuarioId, grupoModel.getId(), "desassociar"));
            });
        }
        return gruposModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> adicionar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }
}
