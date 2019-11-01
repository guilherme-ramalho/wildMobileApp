package br.com.nanothings.wildmobile.model;

import java.io.Serializable;
import java.util.Date;

public class Sorteio implements Serializable {
    private int id;
    private String status, data;
    private Date dataOriginal;
    private Resultado resultados;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDataOriginal() {
        return dataOriginal;
    }

    public void setDataOriginal(Date dataOriginal) {
        this.dataOriginal = dataOriginal;
    }

    public Resultado getResultados() {
        return resultados;
    }

    public void setResultados(Resultado resultados) {
        this.resultados = resultados;
    }
}
