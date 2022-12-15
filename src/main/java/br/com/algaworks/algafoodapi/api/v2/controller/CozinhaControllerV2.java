package br.com.algaworks.algafoodapi.api.v2.controller;

import br.com.algaworks.algafoodapi.api.v2.converter.CozinhaDomainConverterV2;
import br.com.algaworks.algafoodapi.api.v2.converter.CozinhaOutputConverterV2;
import br.com.algaworks.algafoodapi.api.v2.model.CozinhaOutputV2;
import br.com.algaworks.algafoodapi.api.v2.model.input.CozinhaInputV2;
import br.com.algaworks.algafoodapi.api.v2.openapi.controller.CozinhaControllerV2OpenApi;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import br.com.algaworks.algafoodapi.domain.service.CozinhaService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerV2OpenApi {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaOutputConverterV2 cozinhaOutputConverter;

    @Autowired
    private CozinhaDomainConverterV2 cozinhaDomainConverter;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    //private static final Logger logger = LoggerFactory.getLogger(CozinhaController.class);

    @GetMapping
    public PagedModel<CozinhaOutputV2> listar(@PageableDefault(size = 2) Pageable pageable) {
        //logger.info("Consultando cozinhzas com páginas de {} registros...", pageable.getPageSize());
        log.info("Consultando cozinhzas com páginas de {} registros...", pageable.getPageSize());

//        if (true) {
//            throw new RuntimeException("Teste de Exception");
//        }

        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        PagedModel<CozinhaOutputV2> cozinhaOutputPagedModel = pagedResourcesAssembler
                .toModel(cozinhasPage, cozinhaOutputConverter);

        return cozinhaOutputPagedModel;
    }

    @GetMapping(value = "/{id}")
    public CozinhaOutputV2 buscarPeloId(@PathVariable Long id) {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
        return cozinhaOutputConverter.toModel(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaOutputV2 salvar(@RequestBody @Valid CozinhaInputV2 cozinhaInput) {
        Cozinha cozinha = cozinhaDomainConverter.toDomainObject(cozinhaInput);
        return cozinhaOutputConverter.toModel(cozinhaService.salvar(cozinha));
    }

    @PutMapping(value = "/{id}")
    public CozinhaOutputV2 atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputV2 cozinhaInput) {
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
