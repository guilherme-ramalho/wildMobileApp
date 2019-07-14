package br.com.nanothings.wildmobile.rest;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Meta implements Serializable {
    @Expose
    public String status, mensagem;

    public Meta(String status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }
}
