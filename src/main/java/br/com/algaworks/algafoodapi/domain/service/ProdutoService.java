package br.com.algaworks.algafoodapi.domain.service;

import br.com.algaworks.algafoodapi.domain.exception.ProdutoNaoEncontradoException;
import br.com.algaworks.algafoodapi.domain.model.Produto;
import br.com.algaworks.algafoodapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(()-> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }
}
