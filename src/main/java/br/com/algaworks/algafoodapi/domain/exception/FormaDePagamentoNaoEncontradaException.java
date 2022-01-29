package br.com.algaworks.algafoodapi.domain.exception;

public class FormaDePagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaDePagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaDePagamentoNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de forma de pagamento com código %d", id));
    }
}
