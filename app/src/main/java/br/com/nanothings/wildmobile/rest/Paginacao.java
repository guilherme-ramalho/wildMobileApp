package br.com.nanothings.wildmobile.rest;

import java.io.Serializable;

public class Paginacao implements Serializable {
    public int paginaAtual, totalDePaginas, totalItens;

    public Paginacao() {
        this.paginaAtual = 1;
        this.totalDePaginas = 0;
        this.totalItens = 0;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(int paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

    public int getTotalDePaginas() {
        return totalDePaginas;
    }

    public void setTotalDePaginas(int totalDePaginas) {
        this.totalDePaginas = totalDePaginas;
    }

    public int getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(int totalItens) {
        this.totalItens = totalItens;
    }

    //MÉTODOS
    public void avancarPagina() {
        if (paginaAtual < totalDePaginas) {
            paginaAtual++;
        }
    }

    public boolean ultimaPagina() {
        return paginaAtual == totalDePaginas;
    }
}
