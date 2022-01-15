package br.com.algaworks.algafoodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends JpaRepository<CozinhaRepository,Long> {



    //List<Cozinha> consultarPeloNome(String nome);
}
