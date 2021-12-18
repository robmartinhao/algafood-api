package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> todos();
    Restaurante buscarPeloId(Long id);
    Restaurante adicionar(Restaurante restaurante);
    void remover(Restaurante restaurante);
}
