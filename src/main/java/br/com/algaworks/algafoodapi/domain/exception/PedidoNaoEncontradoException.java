package br.com.algaworks.algafoodapi.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long id) {
        this(String.format("Não existe um cadastro de pedido com código %d", id));
    }
}
