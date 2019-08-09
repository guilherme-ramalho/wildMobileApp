package br.com.nanothings.wildmobile.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Palpite implements Serializable {
    private int idModalidade, inicioCerto, finalCerco;
    private String palpite;
    private BigDecimal valorAposta;

    public int getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(int idModalidade) {
        this.idModalidade = idModalidade;
    }

    public int getInicioCerto() {
        return inicioCerto;
    }

    public void setInicioCerto(int inicioCerto) {
        this.inicioCerto = inicioCerto;
    }

    public int getFinalCerco() {
        return finalCerco;
    }

    public void setFinalCerco(int finalCerco) {
        this.finalCerco = finalCerco;
    }

    public String getPalpite() {
        return palpite;
    }

    public void setPalpite(String palpite) {
        this.palpite = palpite;
    }

    public BigDecimal getValorAposta() {
        return valorAposta;
    }

    public void setValorAposta(BigDecimal valorAposta) {
        this.valorAposta = valorAposta;
    }
}