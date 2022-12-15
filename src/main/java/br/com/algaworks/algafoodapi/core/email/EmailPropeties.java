package br.com.algaworks.algafoodapi.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
//@ConfigurationProperties("algafood.email")
public class EmailPropeties {

    @NotNull
    private String remetente;

    private Sandbox sandbox = new Sandbox();

    private Implementacao impl = Implementacao.FAKE;

    public enum Implementacao {
        SMTP, FAKE, SANDBOX
    }

    @Getter
    @Setter
    public class Sandbox {
        private String destinatario;
    }
}
