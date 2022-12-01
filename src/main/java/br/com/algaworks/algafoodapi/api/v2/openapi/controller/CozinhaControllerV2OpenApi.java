package br.com.algaworks.algafoodapi.api.v2.openapi.controller;

import br.com.algaworks.algafoodapi.api.v2.model.CozinhaOutputV2;
import br.com.algaworks.algafoodapi.api.v2.model.input.CozinhaInputV2;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public interface CozinhaControllerV2OpenApi {

    PagedModel<CozinhaOutputV2> listar(Pageable pageable);

    CozinhaOutputV2 buscarPeloId(
            Long cozinhaId);

    CozinhaOutputV2 salvar(
            CozinhaInputV2 cozinhaInput);

    CozinhaOutputV2 atualizar(
            Long cozinhaId,

            CozinhaInputV2 cozinhaInput);

    ResponseEntity<Void> remover(
            Long cozinhaId);
}