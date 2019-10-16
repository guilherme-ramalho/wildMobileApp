package br.com.nanothings.wildmobile.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class Apuracao implements Serializable {
    @SerializedName("quantidadeApostas")
    private int qtdApostas;
    private BigDecimal limiteDisponivel, valorApostado, valorComissao, valorPremiacao, valorLiquido;

    public int getQtdApostas() {
        return qtdApostas;
    }

    public void setQtdApostas(int qtdApostas) {
        this.qtdApostas = qtdApostas;
    }

    public BigDecimal getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(BigDecimal limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    public BigDecimal getValorApostado() {
        return valorApostado;
    }

    public void setValorApostado(BigDecimal valorApostado) {
        this.valorApostado = valorApostado;
    }

    public BigDecimal getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(BigDecimal valorComissao) {
        this.valorComissao = valorComissao;
    }

    public BigDecimal getValorPremiacao() {
        return valorPremiacao;
    }

    public void setValorPremiacao(BigDecimal valorPremiacao) {
        this.valorPremiacao = valorPremiacao;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }
}
