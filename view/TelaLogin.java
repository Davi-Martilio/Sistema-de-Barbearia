package view;

import dao.UsuarioDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaLogin extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public TelaLogin() {

        setTitle("Login");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblTitulo = new JLabel("LOGIN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));

        txtUsuario = new JTextField();
        txtSenha = new JPasswordField();

        txtUsuario.setPreferredSize(new Dimension(250, 30));
        txtSenha.setPreferredSize(new Dimension(250, 30));

        JButton btnEntrar = new JButton("Entrar");

        btnEntrar.addActionListener(e -> {

            try {

                UsuarioDAO dao = new UsuarioDAO();

                boolean valido = dao.validarLogin(
                        txtUsuario.getText(),
                        new String(txtSenha.getPassword())
                );

                if (valido) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Login realizado com sucesso!"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Usuário ou senha inválidos!"
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage()
                );
            }
        });

        JPanel painelCadastro =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                5,
                                0
                        )
                );

        JLabel lblTexto =
                new JLabel(
                        "Ainda não tem uma conta?"
                );

        JLabel lblCadastrar =
                new JLabel(
                        "<html><u>Cadastrar</u></html>"
                );

        lblCadastrar.setForeground(Color.BLUE);
        lblCadastrar.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        lblCadastrar.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        new TelaCadastro().setVisible(true);

                        dispose();
                    }
                }
        );

        painelCadastro.add(lblTexto);
        painelCadastro.add(lblCadastrar);

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
        gbc.insets = new Insets(0, 0, 15, 0);
        painel.add(txtSenha, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(5, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(btnEntrar, gbc);

        gbc.gridy = 6;
        painel.add(painelCadastro, gbc);

        add(painel);
    }
}