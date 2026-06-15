package model;

public class Usuario extends Pessoa {

    public Usuario(String usuario, String senha) {
        super(usuario, senha);
    }

    @Override
    public String getTipo() {
        return "CLIENTE";
    }
}