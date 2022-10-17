package br.com.algaworks.algafoodapi.api.v1.controller;

import br.com.algaworks.algafoodapi.api.v1.converter.output.FotoProdutoOutputConverter;
import br.com.algaworks.algafoodapi.api.v1.model.dto.input.FotoProdutoInput;
import br.com.algaworks.algafoodapi.api.v1.model.dto.output.FotoProdutoOutput;
import br.com.algaworks.algafoodapi.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.FotoProduto;
import br.com.algaworks.algafoodapi.domain.service.CatalogoFotoProdutoService;
import br.com.algaworks.algafoodapi.domain.service.FotoStorageService;
import br.com.algaworks.algafoodapi.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Autowired
    private FotoProdutoOutputConverter fotoProdutoOutputConverter;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoOutput atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                           @Valid FotoProdutoInput fotoProdutoInput,
                                           @RequestPart(required = true) MultipartFile arquivo) throws IOException {
        var produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        var foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());

        return fotoProdutoOutputConverter.toModel(fotoSalva);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProdutoService.excluir(restauranteId, produtoId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoOutput pesquisar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoEncontrada = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return fotoProdutoOutputConverter.toModel(fotoEncontrada);
    }

    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                                          @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoEncontrada = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoEncontrada.getContentType());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoEncontrada.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
                                                   List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }

    }
}
