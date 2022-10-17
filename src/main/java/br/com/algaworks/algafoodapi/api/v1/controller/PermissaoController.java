package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.output.PermissaoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.PermissaoOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.PermissaoControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.Permissao;
import br.com.algaworks.algafoodapi.domain.repository.PermissaoRepository;
import br.com.algaworks.algafoodapi.domain.service.PermissaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/permissao")
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private PermissaoService permissaoService;

    @Autowired
    private PermissaoOutputConverter permissaoOutputConverter;

    @GetMapping
    public CollectionModel<PermissaoOutput> listar() {
        return permissaoOutputConverter.toCollectionModel(permissaoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permissao> buscarPeloId(@PathVariable Long id) {
        Optional<Permissao> permissaoEncontrada = permissaoRepository.findById(id);
        if (permissaoEncontrada.isPresent()) {
            return ResponseEntity.ok(permissaoEncontrada.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Permissao salvar(@RequestBody Permissao permissao) {
        return permissaoService.salvar(permissao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permissao> atualizar(@PathVariable Long id, @RequestBody Permissao permissao) {
        Optional<Permissao> permissaoEncontrada = permissaoRepository.findById(id);

        if (permissaoEncontrada.isPresent()) {
            BeanUtils.copyProperties(permissao, permissaoEncontrada.get(), "id");
            Permissao permissaoSalva = permissaoService.salvar(permissaoEncontrada.get());
            return ResponseEntity.ok(permissaoSalva);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Permissao> remover(@PathVariable Long id) {
        try {
            permissaoService.excluir(id);
            return ResponseEntity.noContent().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();

        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
