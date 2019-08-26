package br.com.nanothings.wildmobile.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Aposta implements Serializable {
    private int id;
    private String codigo, status, nomeApostador;
    private BigDecimal valorAposta, valorPremio, valorComissao;
    @SerializedName("dataOriginal")
    private Date data;
    private List<Palpite> palpites;

    public Aposta() {
        this.palpites = new ArrayList<>();
        this.valorAposta = BigDecimal.ZERO;
        this.valorPremio = BigDecimal.ZERO;
        this.valorComissao = BigDecimal.ZERO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeApostador() {
        return nomeApostador;
    }

    public void setNomeApostador(String nomeApostador) {
        this.nomeApostador = nomeApostador;
    }

    public BigDecimal getValorAposta() {
        return valorAposta;
    }

    public void setValorAposta(BigDecimal valorAposta) {
        this.valorAposta = valorAposta;
    }

    public BigDecimal getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(BigDecimal valorPremio) {
        this.valorPremio = valorPremio;
    }

    public BigDecimal getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(BigDecimal valorComissao) {
        this.valorComissao = valorComissao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Palpite> getPalpites() {
        return palpites;
    }

    public void setPalpites(List<Palpite> palpites) {
        this.palpites = palpites;
    }

    public void addValorAposta(BigDecimal apostaPalpite) {
        this.valorAposta = this.valorAposta.add(apostaPalpite);
    }

    public void addPremioPalpite(BigDecimal premioPalpite) {
        this.valorPremio = this.valorPremio.add(premioPalpite);
    }
}
