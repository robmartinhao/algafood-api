package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.output.FotoProdutoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.FotoProdutoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.FotoProdutoOutput;
import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.FotoProduto;
import br.com.algaworks.algafoodapi.domain.service.CatalogoFotoProdutoService;
import br.com.algaworks.algafoodapi.domain.service.FotoStorageService;
import br.com.algaworks.algafoodapi.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Autowired
    private FotoProdutoOutputConverter fotoProdutoOutputConverter;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoOutput atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                           @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        var produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        var foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto,arquivo.getInputStream());

        return fotoProdutoOutputConverter.toFotoProdutoOutput(fotoSalva);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoOutput pesquisar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoEncontrada = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return fotoProdutoOutputConverter.toFotoProdutoOutput(fotoEncontrada);
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        try {


            FotoProduto fotoEncontrada = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
            InputStream inputStream = fotoStorageService.recuperar(fotoEncontrada.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
