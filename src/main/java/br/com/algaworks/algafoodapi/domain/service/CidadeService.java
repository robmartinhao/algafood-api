package br.com.algaworks.algafoodapi.domain.service;

import br.com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.Cidade;
import br.com.algaworks.algafoodapi.domain.model.Estado;
import br.com.algaworks.algafoodapi.domain.repository.CidadeRepository;
import br.com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Optional<Estado> estadoEncontrado = estadoRepository.findById(estadoId);

        if (estadoEncontrado.isPresent()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de estado com código %d", estadoId)
            );
        }
        cidade.setEstado(estadoEncontrado.get());
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com código %d", id));
        }
    }
}
