package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.domain.FormaPagamentoDomainConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.FormaPagamentoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.FormaPagamentoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FormaPagamentoOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import br.com.algaworks.algafoodapi.core.security.CheckSecurity;
import br.com.algaworks.algafoodapi.domain.exception.FormaDePagamentoNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import br.com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;
import br.com.algaworks.algafoodapi.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoOutputConverter formaPagamentoOutputConverter;

    @Autowired
    private FormaPagamentoDomainConverter formaPagamentoDomainConverter;

    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<FormaPagamentoOutput>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        CollectionModel<FormaPagamentoOutput> formasPagamentoOutput = formaPagamentoOutputConverter
                .toCollectionModel(formaPagamentoRepository.findAll());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formasPagamentoOutput);
    }

    @CheckSecurity.FormasPagamento.PodeConsultar
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormaPagamentoOutput> buscarPeloId(@PathVariable Long id, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataAtualizacaoById(id);

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamentoOutput formaPagamentoOutput = formaPagamentoOutputConverter
                .toModel(formaPagamentoService.buscarOuFalhar(id));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(eTag)
                .body(formaPagamentoOutput);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoOutput salvar(@Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoDomainConverter.toDomainObject(formaPagamentoInput);
        return formaPagamentoOutputConverter.toModel(formaPagamentoService.salvar(formaPagamento));
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PutMapping(path = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public FormaPagamentoOutput atualizar(@PathVariable Long id, @RequestBody FormaPagamentoInput formaPagamentoInput) {
        try {
            FormaPagamento formaPagamentoEncontrada = formaPagamentoService.buscarOuFalhar(id);

            formaPagamentoDomainConverter.copyToDomainObject(formaPagamentoInput, formaPagamentoEncontrada);

            return formaPagamentoOutputConverter.toModel(formaPagamentoService.salvar(formaPagamentoEncontrada));

        } catch (FormaDePagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        formaPagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
