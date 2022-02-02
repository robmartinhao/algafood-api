package br.com.algaworks.algafoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    MENSAGEM_INCOMPREENSIVEL("/mesangem-incompreensivel","Mensagem incompreensível"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
    PARAMETRO_INVALIDO("/paramentro-invalido", "Parâmetro Inválido"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado","Recurso não encontrado"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema");

    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.title = title;
        this.uri = "https://algafood.com.br/" + path;
    }
}
