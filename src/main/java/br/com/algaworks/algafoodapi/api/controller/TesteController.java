package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping("/cozinhas/pelo-nome")
    public List<Cozinha> cozinhasPeloNome(String nome) {
        return cozinhaRepository.findTodasByNome(nome);
    }

    @GetMapping("/cozinhas/unico-pelo-nome")
    public Optional<Cozinha> unicaCozinhasPeloNome(String nome) {
        return cozinhaRepository.findByNome(nome);
    }
}