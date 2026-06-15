package model;

public class Barbeiro extends Usuario {
    private String especialidade;

    public Barbeiro(String nome, String senha, String especialidade) {
        super(nome, senha);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + especialidade;
    }
}