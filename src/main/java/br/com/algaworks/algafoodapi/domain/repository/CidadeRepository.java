package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();
    Cidade buscarPeloId(Long id);
    Cidade salvar(Cidade cidade);
    void remover(Long id);
}
