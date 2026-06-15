package model;

import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    protected String nome;
    protected String cpf;
    protected String cep;
    protected String email;
    protected String telefone;
    protected String senha;
    
    public Pessoa(String nome, String cpf, String cep, String email, String telefone, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.cep = cep;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    // Método abstrato para polimorfismo
    public abstract String getTipo();
    
    @Override
    public String toString() {
        return nome + ";" + cpf + ";" + cep + ";" + email + ";" + telefone + ";" + senha;
    }
}