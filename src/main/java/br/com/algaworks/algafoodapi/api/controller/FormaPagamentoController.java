package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import br.com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;
import br.com.algaworks.algafoodapi.domain.service.FormaPagamentoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forma-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @GetMapping
    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamento> buscarPeloId(@PathVariable Long id) {
        Optional<FormaPagamento> formaPagamentoEncontrada = formaPagamentoRepository.findById(id);
        if (formaPagamentoEncontrada.isPresent()) {
            return ResponseEntity.ok(formaPagamentoEncontrada.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamento salvar(@RequestBody FormaPagamento formaPagamento) {
        return formaPagamentoService.salvar(formaPagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamento> atualizar(@PathVariable Long id, @RequestBody FormaPagamento formaPagamento) {
        Optional<FormaPagamento> formaPagamentoEncontrada = formaPagamentoRepository.findById(id);

        if (formaPagamentoEncontrada.isPresent()) {
            BeanUtils.copyProperties(formaPagamento, formaPagamentoEncontrada.get(), "id");
            FormaPagamento formaPagamentoSalva = formaPagamentoService.salvar(formaPagamentoEncontrada.get());
            return ResponseEntity.ok(formaPagamentoSalva);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FormaPagamento> remover(@PathVariable Long id) {
        try {
            formaPagamentoService.excluir(id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
