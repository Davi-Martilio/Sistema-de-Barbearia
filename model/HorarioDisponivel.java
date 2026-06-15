package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HorarioDisponivel {
    private String nomeBarbeiro;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private int duracaoMinutos; // 30 minutos padrão
    private boolean disponivel;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public HorarioDisponivel(String nomeBarbeiro, LocalDateTime inicio, LocalDateTime fim, int duracaoMinutos) {
        this.nomeBarbeiro = nomeBarbeiro;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = fim;
        this.duracaoMinutos = duracaoMinutos;
        this.disponivel = true;
    }
    
    // Getters e Setters
    public String getNomeBarbeiro() { return nomeBarbeiro; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public int getDuracaoMinutos() { return duracaoMinutos; }
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
    
    public String getHorarioFormatado() {
        return dataHoraInicio.format(FORMATTER) + " até " + dataHoraFim.format(FORMATTER);
    }
    
    @Override
    public String toString() {
        return nomeBarbeiro + ";" + dataHoraInicio.format(FORMATTER) + ";" + 
               dataHoraFim.format(FORMATTER) + ";" + duracaoMinutos + ";" + disponivel;
    }
}