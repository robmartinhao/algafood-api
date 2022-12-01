package br.com.algaworks.algafoodapi.api.v1.openapi.controller;

import br.com.algaworks.algafoodapi.api.v1.model.dto.input.FotoProdutoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FotoProdutoOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RestauranteProdutoFotoControllerOpenApi {
    FotoProdutoOutput atualizarFoto(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput,
                                    MultipartFile arquivo)
    throws IOException;

    ResponseEntity<Void> excluir(Long restauranteId, Long produtoId);

    FotoProdutoOutput pesquisar(Long restauranteId, Long produtoId);

    ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;
}
