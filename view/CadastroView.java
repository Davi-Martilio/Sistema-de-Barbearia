package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CadastroView extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JPasswordField confirmarSenhaField;
    
    public CadastroView() {
        setTitle("Sistema de Barbearia - Cadastro");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(44, 62, 80));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        JLabel titleLabel = new JLabel("📝 CRIAR CONTA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Usuário
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(userLabel, gbc);
        
        usuarioField = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(usuarioField, gbc);
        
        // Senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Senha:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(passLabel, gbc);
        
        senhaField = new JPasswordField(15);
        gbc.gridx = 1;
        mainPanel.add(senhaField, gbc);
        
        // Confirmar Senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel confirmLabel = new JLabel("Confirmar Senha:");
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(confirmLabel, gbc);
        
        confirmarSenhaField = new JPasswordField(15);
        gbc.gridx = 1;
        mainPanel.add(confirmarSenhaField, gbc);
        
        // Botão Cadastrar
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(new Color(52, 152, 219));
        cadastrarButton.setForeground(Color.WHITE);
        cadastrarButton.setFont(new Font("Arial", Font.BOLD, 14));
        cadastrarButton.setFocusPainted(false);
        cadastrarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cadastrarButton, gbc);
        
        // Botão Voltar
        JButton voltarButton = new JButton("Voltar ao Login");
        voltarButton.setBackground(new Color(149, 165, 166));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFont(new Font("Arial", Font.PLAIN, 12));
        voltarButton.setFocusPainted(false);
        voltarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = 5;
        mainPanel.add(voltarButton, gbc);
        
        // Ações
        cadastrarButton.addActionListener(e -> realizarCadastro());
        voltarButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void realizarCadastro() {
        String usuario = usuarioField.getText().trim();
        String senha = new String(senhaField.getPassword());
        String confirmarSenha = new String(confirmarSenhaField.getPassword());
        
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Usuario existingUser = UsuarioDAO.buscarPorNome(usuario);
            if (existingUser != null) {
                JOptionPane.showMessageDialog(this, "Usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Usuario novoUsuario = new Usuario(usuario, senha);
            UsuarioDAO.salvar(novoUsuario);
            
            JOptionPane.showMessageDialog(this, "✅ Cadastro realizado com sucesso!");
            new LoginView();
            dispose();
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}