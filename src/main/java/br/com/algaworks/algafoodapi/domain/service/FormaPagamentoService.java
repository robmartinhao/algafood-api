package br.com.algaworks.algafoodapi.domain.service;

import br.com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import br.com.algaworks.algafoodapi.domain.exception.FormaDePagamentoNaoEncontradaException;
import br.com.algaworks.algafoodapi.domain.model.FormaPagamento;
import br.com.algaworks.algafoodapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormaPagamentoService {

    public static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de Pagamento de código %d não pode ser removida, pois está em uso";
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaDePagamentoNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        }
    }

    public FormaPagamento buscarOuFalhar(Long id ) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(()-> new FormaDePagamentoNaoEncontradaException(id));
    }
}
