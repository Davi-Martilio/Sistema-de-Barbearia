package dao;

import model.Agendamento;
import exception.AgendamentoException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AgendamentoDAO {
    private static final String FILE_PATH = "dados/agendamentos.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public static void salvar(Agendamento a) throws AgendamentoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(a.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new AgendamentoException("Erro ao salvar agendamento: " + e.getMessage());
        }
    }
    
    public static List<Agendamento> listarTodos() throws AgendamentoException {
        List<Agendamento> agendamentos = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return agendamentos;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 4) {
                    LocalDateTime data = LocalDateTime.parse(partes[2], FORMATTER);
                    Agendamento a = new Agendamento(partes[0], partes[1], data);
                    a.setStatus(partes[3]);
                    agendamentos.add(a);
                }
            }
        } catch (IOException e) {
            throw new AgendamentoException("Erro ao listar agendamentos: " + e.getMessage());
        }
        return agendamentos;
    }
    
    public static List<Agendamento> buscarPorUsuario(String nomeUsuario) throws AgendamentoException {
        return listarTodos().stream()
                .filter(a -> a.getNomeUsuario().equalsIgnoreCase(nomeUsuario))
                .collect(Collectors.toList());
    }
    
    public static List<Agendamento> buscarPorBarbeiro(String nomeBarbeiro) throws AgendamentoException {
        return listarTodos().stream()
                .filter(a -> a.getNomeBarbeiro().equalsIgnoreCase(nomeBarbeiro))
                .collect(Collectors.toList());
    }
    
    public static void cancelarAgendamento(String nomeUsuario, LocalDateTime dataHora) throws AgendamentoException {
        List<Agendamento> agendamentos = listarTodos();
        for (Agendamento a : agendamentos) {
            if (a.getNomeUsuario().equalsIgnoreCase(nomeUsuario) && 
                a.getDataHora().equals(dataHora)) {
                a.setStatus("CANCELADO");
                break;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Agendamento a : agendamentos) {
                writer.write(a.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new AgendamentoException("Erro ao cancelar agendamento: " + e.getMessage());
        }
    }
}