# Envio de emails com Spring

## Configurando aplicação

- Existem diversas soluções gratuitas e pagas para envio de emails transacionais
- Os emails transacionais são os emails que são enviados após um cadastro de usuário, realização de compras, envio de notifiação etc.
- Uma das soluções utilizadas é o sendgrid
- Adicionamos um starter do spring email para as configurações iniciais

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
```

- Configuramos também o host, a porta, usuario e senha

```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password="apikeydosengrid"
```

## Implementando o serviço de email

- Criamos uma interface no dominio

```java
public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    class Mensagem {

        private Set<String> destinatarios;

        private String assunto;

        private String corpo;

        public Mensagem(Set<String> destinatarios, String assunto, String corpo) {
            this.destinatarios = destinatarios;
            this.assunto = assunto;
            this.corpo = corpo;
        }

        public Set<String> getDestinatarios() {
            return destinatarios;
        }

        public String getAssunto() {
            return assunto;
        }

        public String getCorpo() {
            return corpo;
        }

        public void setAssunto(String assunto) {
            this.assunto = assunto;
        }

        public void setCorpo(String corpo) {
            this.corpo = corpo;
        }

        public void setDestinatarios(Set<String> destinatarios) {
            this.destinatarios = destinatarios;
        }
    }
}

```

- Configuramos as propriedades de remetente

```properties
algafood.email.remetente=Algafood <naoresponder@algafood.com.br>
```

```java
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {


    private String remente;

    public String getRemente() {
        return remente;
    }

    public void setRemente(String remente) {
        this.remente = remente;
    }
}
```

- Exemplo de implementação do serviço de email

```java
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
```