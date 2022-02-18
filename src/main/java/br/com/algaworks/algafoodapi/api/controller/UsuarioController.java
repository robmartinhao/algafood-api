package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.UsuarioDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.UsuarioOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.SenhaInput;
import br.com.algaworks.algafoodapi.api.model.dto.input.UsuarioInput;
import br.com.algaworks.algafoodapi.api.model.dto.input.UsuarioComSenhaInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.UsuarioOutput;
import br.com.algaworks.algafoodapi.domain.model.Usuario;
import br.com.algaworks.algafoodapi.domain.repository.UsuarioRepository;
import br.com.algaworks.algafoodapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioOutputConverter usuarioOutputConverter;

    @Autowired
    private UsuarioDomainConverter usuarioDomainConverter;

    @GetMapping
    public List<UsuarioOutput> listar() {
        return usuarioOutputConverter.toCollectionUsuarioOutput(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioOutput buscarPeloId(@PathVariable Long id) {
        return usuarioOutputConverter.toUsuarioOutput(usuarioService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioOutput salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioDomainConverter.toDomainObject(usuarioInput);
        return usuarioOutputConverter.toUsuarioOutput(usuarioService.salvar(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioOutput atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioEncontrado = usuarioService.buscarOuFalhar(id);
        usuarioDomainConverter.copyToDomainObject(usuarioInput, usuarioEncontrado);

        return usuarioOutputConverter.toUsuarioOutput(usuarioService.salvar(usuarioEncontrado));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        usuarioService.excluir(id);
    }

    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
          usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }
}
