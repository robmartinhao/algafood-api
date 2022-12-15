package br.com.algaworks.algafoodapi;

import br.com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.service.CozinhaService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CozinhaServiceIntegrationTestsIT {

    @Autowired
    CozinhaService cozinhaService;

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
        var novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        cozinhaService.salvar(novaCozinha);

        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
        var novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado =
                Assertions.assertThrows(ConstraintViolationException.class, () ->
                        cozinhaService.salvar(novaCozinha)
                );
        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso() {
        EntidadeEmUsoException erroEsperado =
                Assertions.assertThrows(EntidadeEmUsoException.class, () ->
                        cozinhaService.excluir(1L)
                );
        assertThat(erroEsperado).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente() {
        EntidadeNaoEncontradaException erroEsperado =
                Assertions.assertThrows(EntidadeNaoEncontradaException.class, () ->
                        cozinhaService.excluir(999999L)
                );
        assertThat(erroEsperado).isNotNull();
    }
}
