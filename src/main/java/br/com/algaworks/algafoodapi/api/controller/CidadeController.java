package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.CidadeDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.output.CidadeOutputConverter;
import br.com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import br.com.algaworks.algafoodapi.api.model.dto.input.CidadeInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.CidadeOutput;
import br.com.algaworks.algafoodapi.domain.exception.EstadoNaoEncontradoException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import br.com.algaworks.algafoodapi.domain.service.CidadeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeOutputConverter cidadeOutputConverter;

    @Autowired
    private CidadeDomainConverter cidadeDomainConverter;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeOutput> listar() {
        return cidadeOutputConverter.toCollectionCidadeOutput(cidadeRepository.findAll());
    }

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @GetMapping("/{id}")
    public CidadeOutput buscarPeloId(@ApiParam(value = "ID de uma cidade") @PathVariable Long id) {
        return cidadeOutputConverter.toCidadeOutput(cidadeService.buscarOuFalhar(id));
    }

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cidade cadastrada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeOutput salvar(@ApiParam(value = "Representação de uma nova cidade")
                               @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeDomainConverter.toDomainObject(cidadeInput);
            return cidadeOutputConverter.toCidadeOutput(cidadeService.salvar(cidade));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code= 200, message = "Cidade atualizada"),
            @ApiResponse(code= 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @PutMapping("/{id}")
    public CidadeOutput atualizar(@ApiParam(value = "ID de uma cidade")
                                  @PathVariable Long id,
                                  @ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados")
                                  @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeEncontrada = cidadeService.buscarOuFalhar(id);

            cidadeDomainConverter.copyToDomainObject(cidadeInput, cidadeEncontrada);
            //BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");
            return cidadeOutputConverter.toCidadeOutput(cidadeService.salvar(cidadeEncontrada));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code= 204, message = "Cidade excluída"),
            @ApiResponse(code= 404, message = "Cidade não encontrada", response = Problem.class)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
            @ApiParam(value = "ID de uma cidade")
            @PathVariable Long id) {
        cidadeService.excluir(id);
    }
}
