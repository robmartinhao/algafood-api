package br.com.algaworks.algafoodapi.infrastructure.service.email;

import br.com.algaworks.algafoodapi.core.email.EmailPropeties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Slf4j
public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

    @Autowired
    private EmailPropeties emailPropeties;

    @Override
    public MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(mensagem);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailPropeties.getSandbox().getDestinatario());
        return mimeMessage;
    }
}
