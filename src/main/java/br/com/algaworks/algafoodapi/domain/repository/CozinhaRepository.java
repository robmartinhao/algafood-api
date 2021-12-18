package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> todas();
    Cozinha buscarPeloId(Long id);
    Cozinha adicionar(Cozinha cozinha);
    void remover(Cozinha cozinha);
}
