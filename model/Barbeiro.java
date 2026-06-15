package model;

public class Barbeiro extends Pessoa {

    public Barbeiro(String usuario, String senha) {
        super(usuario, senha);
    }

    @Override
    public String getTipo() {
        return "BARBEIRO";
    }
}