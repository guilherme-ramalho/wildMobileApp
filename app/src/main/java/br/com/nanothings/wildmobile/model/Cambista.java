package br.com.nanothings.wildmobile.model;

public class Cambista {
    private int id, idRegional, idSupervisor;
    private boolean flagAtivo;
    private String nome, usuario, senha;
    private Sessao sessao;
    private Regional regional;
    private Supervisor supervisor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(int idRegional) {
        this.idRegional = idRegional;
    }

    public int getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public boolean isFlagAtivo() {
        return flagAtivo;
    }

    public void setFlagAtivo(boolean flagAtivo) {
        this.flagAtivo = flagAtivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public Regional getRegional() {
        return regional;
    }

    public void setRegional(Regional regional) {
        this.regional = regional;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
