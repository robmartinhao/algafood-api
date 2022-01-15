package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();
    List<Cozinha> consultarPeloNome(String nome);
    Cozinha buscarPeloId(Long id);
    Cozinha salvar(Cozinha cozinha);
    void remover(Long id);
}
