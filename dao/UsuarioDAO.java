package dao;

import model.Usuario;
import java.io.*;
import java.util.*;

public class UsuarioDAO {
    private static final String FILE_PATH = "dados/usuarios.txt";

    public static void salvar(Usuario u) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(u.toString());
            writer.newLine();
        }
    }

    public static List<Usuario> listarTodos() throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return usuarios;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 2) {
                    usuarios.add(new Usuario(partes[0], partes[1]));
                }
            }
        }
        return usuarios;
    }

    public static Usuario buscarPorNome(String nome) throws IOException {
        return listarTodos().stream()
                .filter(u -> u.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }
}