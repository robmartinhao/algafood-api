package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.AlgaLinks;
import br.com.algaworks.algafoodapi.api.converter.output.FormaPagamentoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.output.FormaPagamentoOutput;
import br.com.algaworks.algafoodapi.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoOutputConverter formaPagamentoOutputConverter;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<FormaPagamentoOutput> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoOutput> formasPagamentoOutput
                = formaPagamentoOutputConverter.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
                .add(algaLinks.linkToRestauranteFormasPagamentoAssociacao(restauranteId, "associar"));

        formasPagamentoOutput.getContent().forEach(formaPagamentoOutput -> {
            formaPagamentoOutput.add(algaLinks.linkToRestauranteFormasPagamentoDesassociacao(
                    restauranteId, formaPagamentoOutput.getId(), "desassociar"));
        });
        return formasPagamentoOutput;
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }
}
