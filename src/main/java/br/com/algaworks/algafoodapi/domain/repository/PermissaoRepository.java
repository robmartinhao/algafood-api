package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> todas();
    Permissao buscarPeloId(Long id);
    Permissao adicionar(Permissao permissao);
    void remover(Permissao permissao);
}
