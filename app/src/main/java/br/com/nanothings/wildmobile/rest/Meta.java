package br.com.nanothings.wildmobile.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meta implements Serializable {
    @Expose
    public String status, mensagem;

    @Expose
    @SerializedName("codStatus")
    public int codigoStatus;

    public Paginacao paginacao;

    public Meta(String status, String mensagem, Paginacao paginacao) {
        this.status = status;
        this.mensagem = mensagem;
        this.paginacao = paginacao;
    }
}
