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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<FormaPagamentoOutput> listar() {
        return formaPagamentoOutputConverter.toCollectionFormaPagamentoOutput(formaPagamentoRepository.findAll());
    }

    @GetMapping("/{id}")
    public FormaPagamentoOutput buscarPeloId(@PathVariable Long id) {
        return formaPagamentoOutputConverter.toFormaPagamentoOutput(formaPagamentoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoOutput salvar(@RequestBody FormaPagamentoInput formaPagamentoInput) {
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
