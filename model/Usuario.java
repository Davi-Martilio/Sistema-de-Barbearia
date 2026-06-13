package model;

public class Usuario extends Pessoa {
    private String senha;

    public Usuario(String nome, String senha) {
        super(nome);
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String exibirTipoUsuario() {
        return "Usuário";
    }
}
