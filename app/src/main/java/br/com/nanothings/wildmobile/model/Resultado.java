package br.com.nanothings.wildmobile.model;

import java.io.Serializable;
import java.util.Date;

public class Resultado implements Serializable {
    private int id, idSorteio;
    private String numero;;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSorteio() {
        return idSorteio;
    }

    public void setIdSorteio(int idSorteio) {
        this.idSorteio = idSorteio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
