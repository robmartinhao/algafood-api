package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.CozinhaDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.CozinhaOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.CozinhaInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import br.com.algaworks.algafoodapi.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaOutputConverter cozinhaOutputConverter;

    @Autowired
    private CozinhaDomainConverter cozinhaDomainConverter;

    @GetMapping
    public List<CozinhaOutput> listar() {
        return cozinhaOutputConverter.toCollectionCozinhaOutput(cozinhaRepository.findAll());
    }

    @GetMapping("/{id}")
    public CozinhaOutput buscarPeloId(@PathVariable Long id) {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
        return cozinhaOutputConverter.toCozinhaOutput(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaOutput salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaDomainConverter.toDomainObject(cozinhaInput);
        return cozinhaOutputConverter.toCozinhaOutput(cozinhaService.salvar(cozinha));
    }

    @PutMapping("/{id}")
    public CozinhaOutput atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaEncontrada = cozinhaService.buscarOuFalhar(id);

        cozinhaDomainConverter.copyToDomainObject(cozinhaInput, cozinhaEncontrada);

        //BeanUtils.copyProperties(cozinha, cozinhaEncontrada, "id");
        return cozinhaOutputConverter.toCozinhaOutput(cozinhaService.salvar(cozinhaEncontrada));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cozinhaService.excluir(id);
    }
}
