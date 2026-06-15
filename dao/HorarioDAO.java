package dao;

import model.HorarioDisponivel;
import exception.CadastroException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class HorarioDAO {
    private static final String FILE_PATH = "dados/horarios.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public static void salvar(HorarioDisponivel h) throws CadastroException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(h.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new CadastroException("Erro ao salvar horário: " + e.getMessage());
        }
    }
    
    public static List<HorarioDisponivel> listarTodos() throws CadastroException {
        List<HorarioDisponivel> horarios = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return horarios;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    LocalDateTime inicio = LocalDateTime.parse(partes[1], FORMATTER);
                    LocalDateTime fim = LocalDateTime.parse(partes[2], FORMATTER);
                    HorarioDisponivel h = new HorarioDisponivel(partes[0], inicio, fim, Integer.parseInt(partes[3]));
                    h.setDisponivel(Boolean.parseBoolean(partes[4]));
                    horarios.add(h);
                }
            }
        } catch (IOException e) {
            throw new CadastroException("Erro ao listar horários: " + e.getMessage());
        }
        return horarios;
    }
    
    public static List<HorarioDisponivel> buscarPorBarbeiro(String nomeBarbeiro) throws CadastroException {
        return listarTodos().stream()
                .filter(h -> h.getNomeBarbeiro().equalsIgnoreCase(nomeBarbeiro) && h.isDisponivel())
                .collect(Collectors.toList());
    }
    
    public static void atualizarDisponibilidade(String nomeBarbeiro, LocalDateTime dataHora, boolean disponivel) throws CadastroException {
        List<HorarioDisponivel> horarios = listarTodos();
        for (HorarioDisponivel h : horarios) {
            if (h.getNomeBarbeiro().equalsIgnoreCase(nomeBarbeiro) && 
                h.getDataHoraInicio().equals(dataHora)) {
                h.setDisponivel(disponivel);
                break;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (HorarioDisponivel h : horarios) {
                writer.write(h.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new CadastroException("Erro ao atualizar horário: " + e.getMessage());
        }
    }
}