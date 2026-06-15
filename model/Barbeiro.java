package model;

import java.util.ArrayList;
import java.util.List;

public class Barbeiro extends Pessoa {
    private String especialidade;
    private List<HorarioDisponivel> horariosDisponiveis;
    
    public Barbeiro(String nome, String cpf, String cep, String email, String telefone, 
                    String senha, String especialidade) {
        super(nome, cpf, cep, email, telefone, senha);
        this.especialidade = especialidade;
        this.horariosDisponiveis = new ArrayList<>();
    }
    
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    
    public List<HorarioDisponivel> getHorariosDisponiveis() { return horariosDisponiveis; }
    public void setHorariosDisponiveis(List<HorarioDisponivel> horarios) { this.horariosDisponiveis = horarios; }
    
    public void adicionarHorarioDisponivel(HorarioDisponivel horario) {
        this.horariosDisponiveis.add(horario);
    }
    
    @Override
    public String getTipo() {
        return "BARBEIRO";
    }
    
    @Override
    public String toString() {
        return "BARBEIRO;" + super.toString() + ";" + especialidade;
    }
}