package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import br.com.algaworks.algafoodapi.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscarPeloId(@PathVariable Long id) {
        Optional<Cidade> cidadeEncontrada = cidadeRepository.findById(id);
        if (cidadeEncontrada.isPresent()) {
            return ResponseEntity.ok(cidadeEncontrada.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Cidade cidade) {
        try {
            Cidade cidadeEncontrada = cidadeService.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeEncontrada);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        try {
            Optional<Cidade> cidadeEncontrada = cidadeRepository.findById(id);
            if (cidadeEncontrada.isPresent()) {
                BeanUtils.copyProperties(cidade, cidadeEncontrada.get(), "id");
                Cidade cidadeSalva = cidadeService.salvar(cidadeEncontrada.get());
                return ResponseEntity.ok(cidadeSalva);
            }
            return ResponseEntity.notFound().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            cidadeService.excluir(id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
