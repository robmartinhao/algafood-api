package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.exception.NegocioException;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurante buscarPeloId(@PathVariable Long id) {
        return restauranteService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante salvar(@RequestBody Restaurante restaurante) {
        try {
            return restauranteService.salvar(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Restaurante restauranteEncontrado = restauranteService.buscarOuFalhar(id);
        BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        try {
            return restauranteService.salvar(restauranteEncontrado);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        restauranteService.excluir(id);
    }

    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Restaurante restauranteEncontrado = restauranteService.buscarOuFalhar(id);
        merge(campos, restauranteEncontrado);
        return atualizar(id, restauranteEncontrado);
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

        System.out.println(restauranteOrigem);

        camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            System.out.println(nomePropriedade + " = " + valorPropriedade + " " + novoValor);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }
}
