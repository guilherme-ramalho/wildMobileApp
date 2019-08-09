package br.com.nanothings.wildmobile.model;

import java.util.ArrayList;
import java.util.List;

public class ModalidadeAposta {
    private int id;
    private String nome;
    private boolean flagAtivo;
    private List<TipoPalpite> tipos_palpite;

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
        return tipos_palpite;
    }

    public void setPalpites(List<TipoPalpite> tipos_palpite) {
        this.tipos_palpite = tipos_palpite;
    }

    public ArrayList<String> getListaPalpites() {
        ArrayList<String> listaPalpites = new ArrayList<>();

        for (TipoPalpite tipoPalpite : tipos_palpite) {
            listaPalpites.add(tipoPalpite.getNome() + " - " + nome);
        }

        return listaPalpites;
    }
}
