package model;

public class Usuario extends Pessoa {
    
    public Usuario(String nome, String cpf, String cep, String email, String telefone, String senha) {
        super(nome, cpf, cep, email, telefone, senha);
    }
    
    @Override
    public String getTipo() {
        return "USUARIO";
    }
    
    @Override
    public String toString() {
        return "USUARIO;" + super.toString();
    }
}