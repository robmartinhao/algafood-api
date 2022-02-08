package br.com.algaworks.algafoodapi;

import br.com.algaworks.algafoodapi.domain.model.Cozinha;
import br.com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import br.com.algaworks.algafoodapi.util.DatabaseCleaner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaControllerTestsIT {

    @LocalServerPort
    private int porta;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    public void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        port = porta;
        basePath = "/cozinhas";

        databaseCleaner.clearTables();

        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", hasSize(2))
            .body("nome", hasItems("Americana", "Tailandesa"));
    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha() {
        given()
            .body("{ \"nome\" : \"Chinesa\" }")
                .contentType(ContentType.JSON)
            .when()
                .post()
            .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    private void prepararDados() {
        var cozinha1 = new Cozinha();
        cozinha1.setNome("Tailandesa");
        cozinhaRepository.save(cozinha1);

        var cozinha2 = new Cozinha();
        cozinha2.setNome("Americana");
        cozinhaRepository.save(cozinha2);
    }
}
