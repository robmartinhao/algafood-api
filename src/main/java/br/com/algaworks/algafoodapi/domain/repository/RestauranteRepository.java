package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();
    Restaurante buscarPeloId(Long id);
    Restaurante salvar(Restaurante restaurante);
    void remover(Long id);
}
