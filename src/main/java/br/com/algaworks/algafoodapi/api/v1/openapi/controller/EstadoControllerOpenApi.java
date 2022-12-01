package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.EstadoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.EstadoOutput;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface EstadoControllerOpenApi {

    CollectionModel<EstadoOutput> listar();

    EstadoOutput buscarPeloId(Long estadoId);

    EstadoOutput salvar(EstadoInput estadoInput);

    EstadoOutput atualizar(Long estadoId, EstadoInput estadoInput);

    ResponseEntity<Void> remover(Long estadoId);
}
