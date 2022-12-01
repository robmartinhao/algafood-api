package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.SenhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.UsuarioComSenhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.UsuarioInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.UsuarioOutput;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsuarioControllerOpenApi {
    List<UsuarioOutput> listar();

    UsuarioOutput buscarPeloId(Long usuarioId);

    UsuarioOutput salvar(UsuarioComSenhaInput usuarioInput);

    UsuarioOutput atualizar(Long usuarioId, UsuarioInput usuarioInput);

    ResponseEntity<Void> atualizarSenha(Long usuarioId, SenhaInput senha);
}
