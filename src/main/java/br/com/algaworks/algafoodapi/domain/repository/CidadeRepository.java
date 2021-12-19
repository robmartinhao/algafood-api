package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> todas();
    Cidade buscarPeloId(Long id);
    Cidade adicionar(Cidade cidade);
    void remover(Cidade cidade);
}
