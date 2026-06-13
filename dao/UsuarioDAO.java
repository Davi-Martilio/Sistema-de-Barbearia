package dao;

import model.Usuario;
import java.io.*;
import java.util.ArrayList;

public class UsuarioDAO {

    private static final String ARQUIVO = "usuarios.txt";

    public void salvarUsuario(Usuario usuario) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true));
        bw.write(usuario.getNome() + ";" + usuario.getSenha());
        bw.newLine();
        bw.close();
    }

    public ArrayList<Usuario> listarUsuarios() throws IOException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) return usuarios;

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] dados = linha.split(";");
            usuarios.add(new Usuario(dados[0], dados[1]));
        }

        br.close();
        return usuarios;
    }

    public boolean validarLogin(String usuario, String senha) throws IOException {
        for (Usuario u : listarUsuarios()) {
            if (u.getNome().equals(usuario) && u.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }
}
