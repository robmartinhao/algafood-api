package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return restauranteRepository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPeloId(@PathVariable Long id) {
        Restaurante restauranteEncontrado = restauranteRepository.buscarPeloId(id);

        if (restauranteEncontrado != null) {
            return ResponseEntity.ok(restauranteEncontrado);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteEncontrado = restauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteEncontrado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteEncontrado = restauranteRepository.buscarPeloId(id);
            if (restauranteEncontrado != null) {
                BeanUtils.copyProperties(restaurante, restauranteEncontrado, "id");

                restauranteService.salvar(restauranteEncontrado);
                return ResponseEntity.ok(restauranteEncontrado);
            }
            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            restauranteService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Restaurante restauranteEncontrado = restauranteRepository.buscarPeloId(id);

        if (restauranteEncontrado == null) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteEncontrado);

        return atualizar(id, restauranteEncontrado);

    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem,Restaurante.class);

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
