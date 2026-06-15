package view;

import javax.swing.*;
import java.awt.*;

public class TelaUsuario extends JFrame {

    public TelaUsuario(String nomeUsuario) {

        setTitle("Menu Principal");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("BARBEARIA XPTO");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bemVindo =
                new JLabel("Bem-vindo, " + nomeUsuario);

        bemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnAgendar =
                new JButton("Agendar Horário");

        JButton btnMeusAgendamentos =
                new JButton("Meus Agendamentos");

        JButton btnPerfil =
                new JButton("Meu Perfil");

        JButton btnSair =
                new JButton("Sair");

        btnSair.addActionListener(e -> {

            new TelaLogin().setVisible(true);

            dispose();
        });

        painel.add(Box.createVerticalStrut(30));
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(20));
        painel.add(bemVindo);
        painel.add(Box.createVerticalStrut(30));

        btnAgendar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMeusAgendamentos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(btnAgendar);
        painel.add(Box.createVerticalStrut(10));

        painel.add(btnMeusAgendamentos);
        painel.add(Box.createVerticalStrut(10));

        painel.add(btnPerfil);
        painel.add(Box.createVerticalStrut(10));

        painel.add(btnSair);

        add(painel);
    }
}