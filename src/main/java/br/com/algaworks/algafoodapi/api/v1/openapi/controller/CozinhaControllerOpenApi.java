package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.CozinhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.core.springdoc.PageableParameter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

    @PageableParameter
    PagedModel<CozinhaOutput> listar(@Parameter(hidden = true) Pageable pageable);

    CozinhaOutput buscarPeloId(Long cozinhaId);

    CozinhaOutput salvar(CozinhaInput cozinhaInput);

    CozinhaOutput atualizar(Long cozinhaId, CozinhaInput cozinhaInput);

    ResponseEntity<Void> remover(Long cozinhaId);
}
