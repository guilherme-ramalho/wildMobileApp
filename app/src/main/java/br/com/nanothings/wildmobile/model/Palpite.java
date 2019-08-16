package br.com.nanothings.wildmobile.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Palpite implements Serializable {
    private int inicioCerco, finalCerco;
    private String numeros;
    private BigDecimal valorAposta;
    private TipoPalpite tipoPalpite;

    public Palpite() {
        this.valorAposta = BigDecimal.ZERO;
        this.inicioCerco = 1;
        this.finalCerco = 1;
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

    public String getNumeros() {
        return numeros;
    }

    public void setNumeros(String numeros) {
        this.numeros = numeros;
    }

    public BigDecimal getValorAposta() {
        return valorAposta;
    }

    public void setValorAposta(BigDecimal valorAposta) {
        this.valorAposta = valorAposta;
    }

    public TipoPalpite getTipoPalpite() {
        return tipoPalpite;
    }

    public void setTipoPalpite(TipoPalpite tipoPalpite) {
        this.tipoPalpite = tipoPalpite;
    }

    public String getTextCerco() {
        return "Do " + this.inicioCerco + "º prêmio ao " + this.finalCerco + "º prêmio";
    }

    public int[] getNumeroArray() {
        int[] intArray = new int[]{};
        String[] strArray = this.numeros.split("-");

        for (int i = 0; i <= strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }

        return intArray;
    }
}