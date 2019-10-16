package br.com.nanothings.wildmobile.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Palpite implements Serializable {
    private int[] numerosArray;
    private int primeiroPremio, ultimoPremio;
    @SerializedName("palpite")
    private String numerosString;
    private BigDecimal valorAposta, valorPremio;
    private TipoPalpite tipoPalpite;
    private Sorteio sorteio;

    public Palpite() {
        this.valorAposta = BigDecimal.ZERO;
        this.valorPremio = BigDecimal.ZERO;
        this.primeiroPremio = 1;
        this.ultimoPremio = 1;
        this.tipoPalpite = new TipoPalpite();
        this.tipoPalpite.setId(1);
    }

    public int getPrimeiroPremio() {
        return primeiroPremio;
    }

    public void setPrimeiroPremio(int primeiroPremio) {
        this.primeiroPremio = primeiroPremio;
    }

    public int getUltimoPremio() {
        return ultimoPremio;
    }

    public void setUltimoPremio(int ultimoPremio) {
        this.ultimoPremio = ultimoPremio;
    }

    public String getNumerosString() {
        return numerosString;
    }

    public void setNumerosString(String numerosString) {
        this.numerosString = numerosString;
        setNumerosArray();
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

    public Sorteio getSorteio() {
        return sorteio;
    }

    public void setSorteio(Sorteio sorteio) {
        this.sorteio = sorteio;
    }

    public String getTextIntervaloPremio() {
        return "Do " + this.primeiroPremio + "º ao " + this.ultimoPremio + "º prêmio";
    }

    public int[] getNumerosArray() {
        return numerosArray;
    }

    public void setNumerosArray() {
        String[] strArray = numerosString.split("-");
        numerosArray = new int[strArray.length];

        for (int i = 0 ; i < strArray.length ; i++){
            numerosArray[i] = Integer.parseInt(strArray[i]);
        }
    }

    public BigDecimal getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(BigDecimal valorPremio) {
        this.valorPremio = valorPremio;
    }
}