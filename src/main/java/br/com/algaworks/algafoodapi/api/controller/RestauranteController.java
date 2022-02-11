package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.dto.CozinhaDTO;
import br.com.algaworks.algafoodapi.api.dto.RestauranteDTO;
import br.com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.exception.ValidacaoException;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping
    public List<RestauranteDTO> listar() {
        return toCollectionDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDTO buscarPeloId(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);

        return toDTO(restaurante);
    }

    @Autowired
    private SmartValidator smartValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO salvar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return toDTO(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante restaurante) {
        try {
            Restaurante restauranteEncontrado = restauranteService.buscarOuFalhar(id);
            BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
            return toDTO(restauranteService.salvar(restauranteEncontrado));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        restauranteService.excluir(id);
    }

    @PatchMapping("/{id}")
    public RestauranteDTO atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteEncontrado = restauranteService.buscarOuFalhar(id);
        merge(campos, restauranteEncontrado, request);
        validate(restauranteEncontrado, "restaurante");

        return atualizar(id, restauranteEncontrado);
    }

    private void validate(Restaurante restauranteEncontrado, String objectName) {
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(restauranteEncontrado, objectName);
        smartValidator.validate(restauranteEncontrado, beanPropertyBindingResult);

        if (beanPropertyBindingResult.hasErrors()) {
            throw new ValidacaoException(beanPropertyBindingResult);
        }
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

            System.out.println(restauranteOrigem);

            camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                System.out.println(nomePropriedade + " = " + valorPropriedade + " " + novoValor);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    private RestauranteDTO toDTO(Restaurante restaurante) {
        CozinhaDTO cozinhaDTO = new CozinhaDTO();
        cozinhaDTO.setId(restaurante.getCozinha().getId());
        cozinhaDTO.setNome(restaurante.getCozinha().getNome());

        RestauranteDTO restauranteDTO = new RestauranteDTO();
        restauranteDTO.setId(restaurante.getId());
        restauranteDTO.setNome(restaurante.getNome());
        restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDTO.setCozinha(cozinhaDTO);

        return restauranteDTO;
    }

    private List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toDTO(restaurante))
                .collect(Collectors.toList());
    }
}
