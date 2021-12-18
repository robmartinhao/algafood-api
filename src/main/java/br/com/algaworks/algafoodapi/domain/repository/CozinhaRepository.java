package br.com.algaworks.algafoodapi.domain.repository;

import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CozinhaRepository extends Repository<Cozinha, Long> {

    List<Cozinha> todas();
    Cozinha buscarPeloId(Long id);
    Cozinha adicionar(Cozinha cozinha);
    void remover(Cozinha cozinha);
}
