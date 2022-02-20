package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.domain.RestauranteDomainConverter;
import br.com.algaworks.algafoodapi.api.converter.input.RestauranteInputConverter;
import br.com.algaworks.algafoodapi.api.converter.output.RestauranteOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.RestauranteInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.RestauranteOutput;
import br.com.algaworks.algafoodapi.domain.exception.CidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.exception.ValidacaoException;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteOutputConverter restauranteOutputConverter;

    @Autowired
    private RestauranteInputConverter restauranteInputConverter;

    @Autowired
    private RestauranteDomainConverter restauranteDomainConverter;

    @GetMapping
    public List<RestauranteOutput> listar() {
        return restauranteOutputConverter.toCollectionRestauranteOutput(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteOutput buscarPeloId(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);

        return restauranteOutputConverter.toRestauranteOutput(restaurante);
    }

    @Autowired
    private SmartValidator smartValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteOutput salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteDomainConverter.toDomainObject(restauranteInput);
            return restauranteOutputConverter.toRestauranteOutput(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public RestauranteOutput atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteEncontrado = restauranteService.buscarOuFalhar(id);

            restauranteDomainConverter.copyToDomainObject(restauranteInput, restauranteEncontrado);

            return restauranteOutputConverter.toRestauranteOutput(restauranteService.salvar(restauranteEncontrado));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        restauranteService.excluir(id);
    }

    @PatchMapping("/{id}")
    public RestauranteOutput atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteEncontrado = restauranteService.buscarOuFalhar(id);
        merge(campos, restauranteEncontrado, request);
        validate(restauranteEncontrado, "restaurante");

        RestauranteInput restauranteEncontradoInput = restauranteInputConverter.toRestauranteInput(restauranteEncontrado);
        return atualizar(id, restauranteEncontradoInput);
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

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechamento(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abertura(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
    }
}
