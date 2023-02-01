package com.fredsonchaves.algafood.domain.service;

import java.util.Set;

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
