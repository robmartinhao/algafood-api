package br.com.algaworks.algafoodapi.core.openapi;

import br.com.algaworks.algafoodapi.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    public Docket apiDocket() {
        var TypeResolver = new TypeResolver();

        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("br.com.algaworks.algafoodapi"))
                    .paths(PathSelectors.any())
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponseMessages())
                .globalResponses(HttpMethod.POST, globalPostResponseMessages())
                .globalResponses(HttpMethod.PUT, globalPutResponseMessages())
                .globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
                .additionalModels(TypeResolver.resolve(Problem.class))
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"));
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(new Contact("AlgaWorks", "https://www.algaworks.com", "contato@algaworks.com"))
                .build();
    }

    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build()
        );
    }

    private List<Response> globalPostResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .build()
        );
    }

    private List<Response> globalPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .build()
        );
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .build()
        );
    }
}
