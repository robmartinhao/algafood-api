package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.FormaPagamentoDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.FormaPagamentoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.FormaPagamentoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.FormaPagamentoOutput;
import br.com.algaworks.algafoodapi.domain.exception.FormaDePagamentoNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import br.com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;
import br.com.algaworks.algafoodapi.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoOutputConverter formaPagamentoOutputConverter;

    @Autowired
    private FormaPagamentoDomainConverter formaPagamentoDomainConverter;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoOutput>> listar() {
        List<FormaPagamentoOutput> formasPagamentoOutput = formaPagamentoOutputConverter
                .toCollectionFormaPagamentoOutput(formaPagamentoRepository.findAll());
        return ResponseEntity.ok()
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
//                .cacheControl(CacheControl.noCache())
//                .cacheControl(CacheControl.noStore())
                .body(formasPagamentoOutput);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoOutput> buscarPeloId(@PathVariable Long id) {
        FormaPagamentoOutput formaPagamentoOutput = formaPagamentoOutputConverter
                .toFormaPagamentoOutput(formaPagamentoService.buscarOuFalhar(id));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoOutput);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoOutput salvar(@Valid @RequestBody FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoDomainConverter.toDomainObject(formaPagamentoInput);
        return formaPagamentoOutputConverter.toFormaPagamentoOutput(formaPagamentoService.salvar(formaPagamento));
    }

    @PutMapping("/{id}")
    public FormaPagamentoOutput atualizar(@PathVariable Long id, @RequestBody FormaPagamentoInput formaPagamentoInput) {
        try {
            FormaPagamento formaPagamentoEncontrada = formaPagamentoService.buscarOuFalhar(id);

            formaPagamentoDomainConverter.copyToDomainObject(formaPagamentoInput, formaPagamentoEncontrada);

            return formaPagamentoOutputConverter.toFormaPagamentoOutput(formaPagamentoService.salvar(formaPagamentoEncontrada));

        } catch (FormaDePagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        formaPagamentoService.excluir(id);
    }
}
