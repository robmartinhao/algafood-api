package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.FormaPagamentoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FormaPagamentoOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
public interface FormaPagamentoControllerOpenApi {

    ResponseEntity<CollectionModel<FormaPagamentoOutput>> listar(ServletWebRequest request);

    ResponseEntity<FormaPagamentoOutput> buscarPeloId(Long formaPagamentoId, ServletWebRequest request);

    FormaPagamentoOutput salvar(FormaPagamentoInput formaPagamentoInput);

    FormaPagamentoOutput atualizar(Long formaPagamentoId,
                                   FormaPagamentoInput formaPagamentoInput);

    ResponseEntity<Void> remover(Long formaPagamentoId);
}
