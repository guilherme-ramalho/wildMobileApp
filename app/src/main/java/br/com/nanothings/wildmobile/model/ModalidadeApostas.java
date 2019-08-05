package br.com.nanothings.wildmobile.model;

import java.util.List;

public class ModalidadeApostas {
    private int id;
    private String nome;
    private boolean flagAtivo;
    private List<TipoPalpite> palpites;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<TipoPalpite> getPalpites() {
        return palpites;
    }

    public void setPalpites(List<TipoPalpite> palpites) {
        this.palpites = palpites;
    }
}
