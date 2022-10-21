package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.domain.CozinhaDomainConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.CozinhaOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.CozinhaInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.CozinhaOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.CozinhaControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import br.com.algaworks.algafoodapi.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaOutputConverter cozinhaOutputConverter;

    @Autowired
    private CozinhaDomainConverter cozinhaDomainConverter;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaOutput> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaOutput> cozinhaOutputPagedModel = pagedResourcesAssembler
                .toModel(cozinhasPage, cozinhaOutputConverter);

        return cozinhaOutputPagedModel;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaOutput buscarPeloId(@PathVariable Long id) {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
        return cozinhaOutputConverter.toModel(cozinha);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaOutput salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaDomainConverter.toDomainObject(cozinhaInput);
        return cozinhaOutputConverter.toModel(cozinhaService.salvar(cozinha));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaOutput atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaEncontrada = cozinhaService.buscarOuFalhar(id);

        cozinhaDomainConverter.copyToDomainObject(cozinhaInput, cozinhaEncontrada);

        //BeanUtils.copyProperties(cozinha, cozinhaEncontrada, "id");
        return cozinhaOutputConverter.toModel(cozinhaService.salvar(cozinhaEncontrada));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        cozinhaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
