package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();
    Estado buscarPeloId(Long id);
    Estado adicionar(Estado estado);
    void remover(Estado estado);
}
