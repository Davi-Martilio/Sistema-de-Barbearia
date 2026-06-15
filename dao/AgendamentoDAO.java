package dao;

import model.Agendamento;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AgendamentoDAO {
    private static final String FILE_PATH = "dados/agendamentos.txt";

    public static void salvar(Agendamento a) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(a.toString());
            writer.newLine();
        }
    }

    public static List<Agendamento> listarTodos() throws IOException {
        List<Agendamento> agendamentos = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return agendamentos;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    agendamentos.add(new Agendamento(partes[0], partes[1], partes[2]));
                }
            }
        }
        return agendamentos;
    }

    public static List<Agendamento> buscarPorUsuario(String nomeUsuario) throws IOException {
        return listarTodos().stream()
                .filter(a -> a.getNomeUsuario().equalsIgnoreCase(nomeUsuario))
                .collect(Collectors.toList());
    }

    public static boolean horarioDisponivel(String nomeBarbeiro, String dataHora) throws IOException {
        return listarTodos().stream()
                .noneMatch(a -> a.getNomeBarbeiro().equalsIgnoreCase(nomeBarbeiro)
                        && a.getDataHora().equals(dataHora));
    }
}