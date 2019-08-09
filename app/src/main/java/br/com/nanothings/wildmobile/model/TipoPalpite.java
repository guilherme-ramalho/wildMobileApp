package br.com.nanothings.wildmobile.model;

public class TipoPalpite {
    private int id, idModalidadeAposta;
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
}
