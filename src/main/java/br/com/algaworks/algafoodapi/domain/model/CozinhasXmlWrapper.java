package br.com.algaworks.algafoodapi.domain.model;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CozinhasXmlWrapper {

    @NonNull
    private List<Cozinha> cozinhas;
}
