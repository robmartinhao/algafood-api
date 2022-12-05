package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.CozinhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CozinhaOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

    PagedModel<CozinhaOutput> listar(Pageable pageable);

    CozinhaOutput buscarPeloId(Long cozinhaId);

    CozinhaOutput salvar(CozinhaInput cozinhaInput);

    CozinhaOutput atualizar(Long cozinhaId, CozinhaInput cozinhaInput);

    ResponseEntity<Void> remover(Long cozinhaId);
}
