# Envio de emails com Spring

## Configurando aplica��o

- Existem diversas solu��es gratuitas e pagas para envio de emails transacionais
- Os emails transacionais s�o os emails que s�o enviados ap�s um cadastro de usu�rio, realiza��o de compras, envio de notifia��o etc.
- Uma das solu��es utilizadas � o sendgrid
- Adicionamos um starter do spring email para as configura��es iniciais

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
```

- Configuramos tamb�m o host, a porta, usuario e senha

```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password="apikeydosengrid"
```

## Implementando o servi�o de email

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

- Exemplo de implementa��o do servi�o de email

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