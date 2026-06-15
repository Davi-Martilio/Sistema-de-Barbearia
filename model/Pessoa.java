package model;

public abstract class Pessoa {

    protected String usuario;
    protected String senha;

    public Pessoa(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public abstract String getTipo();
}