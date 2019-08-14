package br.com.nanothings.wildmobile.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

import br.com.nanothings.wildmobile.helper.Constants;

public class Palpite implements Serializable {
    private int idTipoPalpite, inicioCerco, finalCerco;
    private String palpite;
    private BigDecimal valorAposta;

    public Palpite() {
        this.valorAposta = BigDecimal.ZERO;
        this.inicioCerco = 1;
        this.finalCerco = 1;
    }

    public int getIdTipoPalpite() {
        return idTipoPalpite;
    }

    public void setIdTipoPalpite(int idTipoPalpite) {
        this.idTipoPalpite = idTipoPalpite;
    }

    public int getInicioCerco() {
        return inicioCerco;
    }

    public void setInicioCerco(int inicioCerco) {
        this.inicioCerco = inicioCerco;
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

    public String getTextCerco() {
        return "Do " + this.inicioCerco + "º prêmio ao " + this.finalCerco + "º prêmio";
    }

    public String getTextValorFormatado() {
        return NumberFormat.getInstance(Constants.LOCALE_BRAZIL).format(this.valorAposta);
    }

}