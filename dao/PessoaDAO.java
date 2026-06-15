package dao;

import model.Pessoa;
import model.Usuario;
import model.Barbeiro;
import exception.CadastroException;
import java.io.*;
import java.util.*;

public class PessoaDAO {
    private static final String FILE_PATH = "dados/pessoas.txt";
    
    public static void salvar(Pessoa p) throws CadastroException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(p.toString());
            writer.newLine();
        } catch (IOException e) {
            throw new CadastroException("Erro ao salvar pessoa: " + e.getMessage());
        }
    }
    
    public static List<Pessoa> listarTodos() throws CadastroException {
        List<Pessoa> pessoas = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return pessoas;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes[0].equals("USUARIO") && partes.length >= 7) {
                    pessoas.add(new Usuario(partes[1], partes[2], partes[3], partes[4], partes[5], partes[6]));
                } else if (partes[0].equals("BARBEIRO") && partes.length >= 8) {
                    pessoas.add(new Barbeiro(partes[1], partes[2], partes[3], partes[4], partes[5], partes[6], partes[7]));
                }
            }
        } catch (IOException e) {
            throw new CadastroException("Erro ao listar pessoas: " + e.getMessage());
        }
        return pessoas;
    }
    
    public static Pessoa buscarPorNome(String nome) throws CadastroException {
        return listarTodos().stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }
    
    public static void remover(String nome) throws CadastroException {
        List<Pessoa> pessoas = listarTodos();
        pessoas.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Pessoa p : pessoas) {
                writer.write(p.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new CadastroException("Erro ao remover pessoa: " + e.getMessage());
        }
    }
}