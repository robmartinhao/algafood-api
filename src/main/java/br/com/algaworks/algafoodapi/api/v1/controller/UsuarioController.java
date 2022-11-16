package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.domain.UsuarioDomainConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.UsuarioOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.SenhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.UsuarioComSenhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.UsuarioInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.UsuarioOutput;
import br.com.algaworks.algafoodapi.core.security.CheckSecurity;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import br.com.algaworks.algafoodapi.domain.repository.UsuarioRepository;
import br.com.algaworks.algafoodapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioOutputConverter usuarioOutputConverter;

    @Autowired
    private UsuarioDomainConverter usuarioDomainConverter;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UsuarioOutput> listar() {
        return usuarioOutputConverter.toCollectionModel(usuarioRepository.findAll());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioOutput buscarPeloId(@PathVariable Long id) {
        return usuarioOutputConverter.toModel(usuarioService.buscarOuFalhar(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioOutput salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioDomainConverter.toDomainObject(usuarioInput);
        return usuarioOutputConverter.toModel(usuarioService.salvar(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping("/{usuarioId}")
    public UsuarioOutput atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioEncontrado = usuarioService.buscarOuFalhar(usuarioId);
        usuarioDomainConverter.copyToDomainObject(usuarioInput, usuarioEncontrado);

        return usuarioOutputConverter.toModel(usuarioService.salvar(usuarioEncontrado));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
          usuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }
}
