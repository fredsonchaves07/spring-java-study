package com.fredsonchaves.algafood.api.controller.exceptionHandler;

import java.time.LocalDateTime;

public class Problema {

    private LocalDateTime dataHora;
    private String mensagem;

    private Problema() {

    }

    public static Problema builder() {
        return new Problema();
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Problema setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
        return this;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Problema setMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }
}
