package br.com.algaworks.algafoodapi.api.controller;

import br.com.algaworks.algafoodapi.api.converter.output.FotoProdutoOutputConverter;
import br.com.algaworks.algafoodapi.api.model.dto.input.FotoProdutoInput;
import br.com.algaworks.algafoodapi.api.model.dto.output.FotoProdutoOutput;
import br.com.algaworks.algafoodapi.domain.model.FotoProduto;
import br.com.algaworks.algafoodapi.domain.service.CatalogoFotoProdutoService;
import br.com.algaworks.algafoodapi.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;

    @Autowired
    private FotoProdutoOutputConverter fotoProdutoOutputConverter;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoOutput atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                           @Valid FotoProdutoInput fotoProdutoInput) {
        var produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        var foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto);

        return fotoProdutoOutputConverter.toFotoProdutoOutput(fotoSalva);
    }
}
