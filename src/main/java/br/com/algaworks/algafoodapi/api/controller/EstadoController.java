package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.EstadoDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.EstadoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.EstadoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.EstadoOutput;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import br.com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import br.com.algaworks.algafoodapi.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private EstadoOutputConverter estadoOutputConverter;

    @Autowired
    private EstadoDomainConverter estadoDomainConverter;

    @GetMapping
    public List<EstadoOutput> listar() {
        return estadoOutputConverter.toCollectionEstadoOutput(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoOutput buscarPeloId(@PathVariable Long id) {
        return estadoOutputConverter.toEstadoOutput(estadoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoOutput salvar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoDomainConverter.toDomainObject(estadoInput);
        return estadoOutputConverter.toEstadoOutput(estadoService.salvar(estado));
    }

    @PutMapping("/{id}")
    public EstadoOutput atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoEncontrado = estadoService.buscarOuFalhar(id);

        estadoDomainConverter.copyToDomainObject(estadoInput, estadoEncontrado);
        //BeanUtils.copyProperties(estado, estadoEncontrado, "id");
        return estadoOutputConverter.toEstadoOutput(estadoService.salvar(estadoEncontrado));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        estadoService.excluir(id);
    }
}
