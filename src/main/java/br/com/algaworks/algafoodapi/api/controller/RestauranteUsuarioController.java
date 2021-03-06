package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.output.UsuarioOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.output.UsuarioOutput;
import br.com.algaworks.algafoodapi.api.openapi.controller.RestauranteUsuarioControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.model.Restaurante;
import br.com.algaworks.algafoodapi.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioOutputConverter usuarioOutputConverter;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UsuarioOutput> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        return usuarioOutputConverter.toCollectionUsuarioOutput(restaurante.getResponsaveis());
    }

    @PutMapping(value = "/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adicionar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.associarGrupo(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desassociarGrupo(restauranteId, usuarioId);
    }
}
