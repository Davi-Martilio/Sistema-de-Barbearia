package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Agendamento {
    private String nomeUsuario;
    private String nomeBarbeiro;
    private LocalDateTime dataHora;
    private String status; // "AGENDADO", "CONCLUIDO", "CANCELADO"
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public Agendamento(String nomeUsuario, String nomeBarbeiro, LocalDateTime dataHora) {
        this.nomeUsuario = nomeUsuario;
        this.nomeBarbeiro = nomeBarbeiro;
        this.dataHora = dataHora;
        this.status = "AGENDADO";
    }
    
    // Getters
    public String getNomeUsuario() { return nomeUsuario; }
    public String getNomeBarbeiro() { return nomeBarbeiro; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDataHoraFormatada() {
        return dataHora.format(FORMATTER);
    }
    
    @Override
    public String toString() {
        return nomeUsuario + ";" + nomeBarbeiro + ";" + dataHora.format(FORMATTER) + ";" + status;
    }
}