package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import br.com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/pelo-nome")
    public List<Cozinha> cozinhasPeloNome(String nome) {
        return cozinhaRepository.findTodasByNome(nome);
    }

    @GetMapping("/cozinhas/contendo-nome")
    public List<Cozinha> cozinhasPeloNomeContendo(String nome) {
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/unico-pelo-nome")
    public Optional<Cozinha> unicaCozinhasPeloNome(String nome) {
        return cozinhaRepository.findByNome(nome);
    }

    @GetMapping("restaurantes/por-taxa-frete")
    public ResponseEntity<List<Restaurante>> buscarPelaTaxa(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        List<Restaurante> restaurantesEncontrados = restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);

        if (restaurantesEncontrados != null) {
            return ResponseEntity.ok(restaurantesEncontrados);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("restaurantes/por-nome")
    public ResponseEntity<List<Restaurante>> buscarPorNome(String nome, Long cozinhaId) {
        List<Restaurante> restaurantesEncontrados = restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinhaId);

        if (restaurantesEncontrados != null) {
            return ResponseEntity.ok(restaurantesEncontrados);
        }
        return ResponseEntity.notFound().build();
    }
}