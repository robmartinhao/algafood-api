package br.com.algaworks.algafoodapi.jpa;

import br.com.algaworks.algafoodapi.AlgafoodApiApplication;
import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
        List<Cozinha> cozinhas = cadastroCozinha.listar();
        cozinhas.forEach(cozinha -> System.out.println(cozinha.getNome()));
    }
}
