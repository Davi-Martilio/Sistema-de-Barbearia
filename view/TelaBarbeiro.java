package view;

import javax.swing.*;
import java.awt.*;

public class TelaBarbeiro extends JFrame {

    public TelaBarbeiro(String nomeUsuario) {

        setTitle("Painel do Barbeiro");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JLabel titulo =
                new JLabel("PAINEL DO BARBEIRO");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 24));

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel bemVindo =
                new JLabel("Bem-vindo, " + nomeUsuario);

        bemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnAgendamentos =
                new JButton("Ver Agendamentos");

        JButton btnUsuarios =
                new JButton("Gerenciar Usuários");

        JButton btnRelatorios =
                new JButton("Relatórios");

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

        btnAgendamentos.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUsuarios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRelatorios.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSair.setAlignmentX(Component.CENTER_ALIGNMENT);

        painel.add(btnAgendamentos);
        painel.add(Box.createVerticalStrut(10));

        painel.add(btnUsuarios);
        painel.add(Box.createVerticalStrut(10));

        painel.add(btnRelatorios);
        painel.add(Box.createVerticalStrut(10));

        painel.add(btnSair);

        add(painel);
    }
}