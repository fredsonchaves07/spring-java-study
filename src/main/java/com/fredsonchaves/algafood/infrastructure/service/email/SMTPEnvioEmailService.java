package com.fredsonchaves.algafood.infrastructure.service.email;

import com.fredsonchaves.algafood.core.email.EmailProperties;
import com.fredsonchaves.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SMTPEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setSubject(mensagem.getAssunto());
            mimeMessageHelper.setText(mensagem.getCorpo(), true);
            mimeMessageHelper.setFrom(emailProperties.getRemente());
            mimeMessageHelper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            mailSender.send(mimeMessage);
        } catch (Exception exception) {
            throw new EmailException(exception.getMessage());
        }
    }
}
