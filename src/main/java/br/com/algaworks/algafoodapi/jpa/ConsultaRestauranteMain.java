package br.com.algaworks.algafoodapi.jpa;

import br.com.algaworks.algafoodapi.AlgafoodApiApplication;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaRestauranteMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository RestauranteRepository = applicationContext.getBean(RestauranteRepository.class);
        List<Restaurante> restaurantes = RestauranteRepository.todos();
        restaurantes.forEach(r -> System.out.printf("%s - %f - %s\n", r.getNome(), r.getTaxaFrete(), r.getCozinha().getNome()));
    }
}
