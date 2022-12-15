package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.domain.RestauranteDomainConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.input.RestauranteInputConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.RestauranteApenasNomeOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.RestauranteBasicoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.converter.output.RestauranteOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.RestauranteInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteApenasNomeOutput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteBasicoOutput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.RestauranteOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteControllerOpenApi;
import br.com.algaworks.algafoodapi.core.security.CheckSecurity;
import br.com.algaworks.algafoodapi.domain.exception.*;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

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

    @Autowired
    private RestauranteBasicoOutputConverter restauranteBasicoOutputConverter;

    @Autowired
    private RestauranteApenasNomeOutputConverter restauranteApenasNomeOutputConverter;

//    @JsonView(RestauranteView.Resumo.class)
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<RestauranteBasicoOutput> listar() {

        return restauranteBasicoOutputConverter.toCollectionModel(restauranteRepository.findAll());
    }

//    @JsonView(RestauranteView.ApenasNome.class)
    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(params = "projecao=apenas-nome", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<RestauranteApenasNomeOutput> listarApenasNomes() {
        return restauranteApenasNomeOutputConverter.toCollectionModel(restauranteRepository.findAll());
    }

//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//        List<Restaurante> restaurantes = restauranteRepository.findAll();
//        List<RestauranteOutput> restaurantesOutput = restauranteOutputConverter.toCollectionRestauranteOutput(restaurantes);
//
//        MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesOutput);
//
//        restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//        if ("apenas-nome".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//        } else if ("completo".equals(projecao)) {
//            restaurantesWrapper.setSerializationView(null);
//        }
//
//        return restaurantesWrapper;
//    }

//    @GetMapping
//    public List<RestauranteOutput> listar() {
//        return restauranteOutputConverter.toCollectionRestauranteOutput(restauranteRepository.findAll());
//    }
//
//    @JsonView(RestauranteView.Resumo.class)
//    @GetMapping(params = "projecao=resumo")
//    public List<RestauranteOutput> listarResumido() {
//        return listar();
//    }
//
//    @JsonView(RestauranteView.ApenasNome.class)
//    @GetMapping(params = "projecao=apenas-nome")
//    public List<RestauranteOutput> listarApenasNomes() {
//        return listar();
//    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteOutput buscarPeloId(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);

        return restauranteOutputConverter.toModel(restaurante);
    }

    @Autowired
    private SmartValidator smartValidator;

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteOutput salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteDomainConverter.toDomainObject(restauranteInput);
            return restauranteOutputConverter.toModel(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteOutput atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteEncontrado = restauranteService.buscarOuFalhar(id);

            restauranteDomainConverter.copyToDomainObject(restauranteInput, restauranteEncontrado);

            return restauranteOutputConverter.toModel(restauranteService.salvar(restauranteEncontrado));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        restauranteService.excluir(id);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
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

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        restauranteService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        restauranteService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            restauranteService.ativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
            restauranteService.inativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechamento(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abertura(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);
        return ResponseEntity.noContent().build();
    }
}
