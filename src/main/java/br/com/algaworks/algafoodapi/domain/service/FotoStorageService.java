package br.com.algaworks.algafoodapi.domain.service;

import br.com.algaworks.algafoodapi.infrastructure.service.storage.LocalFotoStorageService;
import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface FotoStorageService {

    public void armazenar(NovaFoto novaFoto);

        @Builder
        @Getter
        class NovaFoto {
            private String nomeArquivo;
            private InputStream inputStream;
        }
}
