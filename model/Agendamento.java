package model;

public class Agendamento {
    private String nomeUsuario;
    private String nomeBarbeiro;
    private String dataHora; // formato "dd/MM/yyyy HH:mm"

    public Agendamento(String nomeUsuario, String nomeBarbeiro, String dataHora) {
        this.nomeUsuario = nomeUsuario;
        this.nomeBarbeiro = nomeBarbeiro;
        this.dataHora = dataHora;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getNomeBarbeiro() {
        return nomeBarbeiro;
    }

    public String getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return nomeUsuario + ";" + nomeBarbeiro + ";" + dataHora;
    }
}