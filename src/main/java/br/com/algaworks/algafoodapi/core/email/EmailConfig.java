package br.com.algaworks.algafoodapi.core.email;

import br.com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import br.com.algaworks.algafoodapi.infrastructure.service.email.FakeEnvioEmailService;
import br.com.algaworks.algafoodapi.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailPropeties emailPropeties;

    @Bean
    public EnvioEmailService envioEmailService() {
        switch (emailPropeties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            default:
                return null;
        }
    }
}
