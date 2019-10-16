package br.com.nanothings.wildmobile.model;

import java.io.Serializable;

public class TipoPalpite implements Serializable {
    private int id, idModalidadeAposta, qtdDigitos, valorMinimo, valorMaximo;
    private String nome;
    private boolean flagAtivo;
    private Double multiplicador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdModalidadeAposta() {
        return idModalidadeAposta;
    }

    public void setIdModalidadeAposta(int idModalidadeAposta) {
        this.idModalidadeAposta = idModalidadeAposta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isFlagAtivo() {
        return flagAtivo;
    }

    public void setFlagAtivo(boolean flagAtivo) {
        this.flagAtivo = flagAtivo;
    }

    public Double getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(Double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public int getQtdDigitos() {
        return qtdDigitos;
    }

    public void setQtdDigitos(int qtdDigitos) {
        this.qtdDigitos = qtdDigitos;
    }

    public int getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public int getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
    }
}
