package br.com.nanothings.wildmobile.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Palpite implements Serializable {
    private int idTipoPalpite, inicioCerto, finalCerco;
    private String palpite;
    private BigDecimal valorAposta;

    public int getIdTipoPalpite() {
        return idTipoPalpite;
    }

    public void setIdTipoPalpite(int idTipoPalpite) {
        this.idTipoPalpite = idTipoPalpite;
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