package br.com.algaworks.algafoodapi.infrastructure.service.email;

import br.com.algaworks.algafoodapi.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


@Component
public class ProcessadorEmailTemplate {

    @Autowired
    private Configuration freeMakerConfig;

    protected String processarTemplate(EnvioEmailService.Mensagem mensagem) {
        try {
            Template template = freeMakerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(
                    template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possível montar o template e-mail.", e);
        }
    }
}
