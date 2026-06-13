package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaCadastro extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;

    public TelaCadastro() {

        setTitle("Cadastro");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblTitulo = new JLabel("CADASTRO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));

        txtUsuario = new JTextField();
        txtSenha = new JPasswordField();
        txtConfirmarSenha = new JPasswordField();

        txtUsuario.setPreferredSize(new Dimension(250, 30));
        txtSenha.setPreferredSize(new Dimension(250, 30));
        txtConfirmarSenha.setPreferredSize(new Dimension(250, 30));

        JButton btnCriarConta =
                new JButton("Criar Conta");

        btnCriarConta.addActionListener(e -> {

            try {

                String usuario =
                        txtUsuario.getText().trim();

                String senha =
                        new String(txtSenha.getPassword());

                String confirmar =
                        new String(txtConfirmarSenha.getPassword());

                if (usuario.isEmpty()
                        || senha.isEmpty()
                        || confirmar.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Preencha todos os campos!"
                    );

                    return;
                }

                if (!senha.equals(confirmar)) {

                    JOptionPane.showMessageDialog(
                            null,
                            "As senhas não coincidem!"
                    );

                    return;
                }

                UsuarioDAO dao =
                        new UsuarioDAO();

                dao.salvarUsuario(
                        new Usuario(usuario, senha)
                );

                JOptionPane.showMessageDialog(
                        null,
                        "Conta criada com sucesso!"
                );

                new TelaLogin().setVisible(true);

                dispose();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage()
                );
            }

        });

        JPanel painelVoltar =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                5,
                                0
                        )
                );

        JLabel lblTexto =
                new JLabel(
                        "Já tem uma conta?"
                );

        JLabel lblVoltar =
                new JLabel(
                        "<html><u>Voltar</u></html>"
                );

        lblVoltar.setForeground(Color.BLUE);

        lblVoltar.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        lblVoltar.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        new TelaLogin().setVisible(true);

                        dispose();
                    }
                }
        );

        painelVoltar.add(lblTexto);
        painelVoltar.add(lblVoltar);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        painel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(2, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(new JLabel("Usuário"), gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        painel.add(txtUsuario, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(2, 0, 0, 0);
        painel.add(new JLabel("Senha"), gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 10, 0);
        painel.add(txtSenha, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(2, 0, 0, 0);
        painel.add(new JLabel("Confirmar Senha"), gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 15, 0);
        painel.add(txtConfirmarSenha, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(5, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnCriarConta, gbc);

        gbc.gridy = 8;
        painel.add(painelVoltar, gbc);

        add(painel);
    }
}