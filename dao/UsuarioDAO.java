package dao;

import model.*;

import java.io.*;
import java.util.ArrayList;

public class UsuarioDAO {

    private static final String ARQUIVO = "usuarios.txt";

    public void salvarUsuario(Pessoa pessoa)
            throws IOException {

        BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(ARQUIVO, true));

        bw.write(
                pessoa.getTipo() + ";" +
                pessoa.getUsuario() + ";" +
                pessoa.getSenha()
        );

        bw.newLine();

        bw.close();
    }

    public ArrayList<Pessoa> listarUsuarios()
            throws IOException {

        ArrayList<Pessoa> lista =
                new ArrayList<>();

        File arquivo =
                new File(ARQUIVO);

        if (!arquivo.exists()) {

            criarAdmin();

            return listarUsuarios();
        }

        BufferedReader br =
                new BufferedReader(
                        new FileReader(arquivo));

        String linha;

        while ((linha = br.readLine()) != null) {

            String[] dados =
                    linha.split(";");

            String tipo = dados[0];
            String usuario = dados[1];
            String senha = dados[2];

            if (tipo.equals("USUARIO")) {

                lista.add(
                        new Usuario(
                                usuario,
                                senha
                        )
                );

            } else {

                lista.add(
                        new Barbeiro(
                                usuario,
                                senha
                        )
                );
            }
        }

        br.close();

        return lista;
    }

    private void criarAdmin()
            throws IOException {

        BufferedWriter bw =
                new BufferedWriter(
                        new FileWriter(ARQUIVO));

        bw.write(
                "BARBEIRO;admin;123"
        );

        bw.newLine();

        bw.close();
    }

    public Pessoa realizarLogin(
            String usuario,
            String senha)
            throws IOException {

        ArrayList<Pessoa> lista =
                listarUsuarios();

        for (Pessoa p : lista) {

            if (p.getUsuario().equals(usuario)
                    && p.getSenha().equals(senha)) {

                return p;
            }
        }

        return null;
    }
}