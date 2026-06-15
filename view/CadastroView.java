package view;

import dao.PessoaDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class CadastroView extends JFrame {
    private JTextField nomeField, cpfField, cepField, emailField, telefoneField;
    private JPasswordField senhaField, confirmarSenhaField;
    
    public CadastroView() {
        setTitle("Sistema de Barbearia - Cadastro");
        setSize(500, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(44, 62, 80));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel titleLabel = new JLabel("📝 CRIAR CONTA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Campos do formulário completo
        String[] labels = {"Nome Completo:", "CPF:", "CEP:", "Email:", "Telefone:", "Senha:", "Confirmar Senha:"};
        JTextField[] fields = {
            nomeField = new JTextField(20),
            cpfField = new JTextField(20),
            cepField = new JTextField(20),
            emailField = new JTextField(20),
            telefoneField = new JTextField(20),
            senhaField = new JPasswordField(20),
            confirmarSenhaField = new JPasswordField(20)
        };
        
        for (int i = 0; i < labels.length; i++) {
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            mainPanel.add(label, gbc);
            
            gbc.gridx = 1;
            mainPanel.add(fields[i], gbc);
        }
        
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(new Color(52, 152, 219));
        cadastrarButton.setForeground(Color.WHITE);
        cadastrarButton.setFont(new Font("Arial", Font.BOLD, 14));
        cadastrarButton.setFocusPainted(false);
        cadastrarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cadastrarButton, gbc);
        
        JButton voltarButton = new JButton("Voltar ao Login");
        voltarButton.setBackground(new Color(149, 165, 166));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFont(new Font("Arial", Font.PLAIN, 12));
        
        gbc.gridy = labels.length + 2;
        mainPanel.add(voltarButton, gbc);
        
        cadastrarButton.addActionListener(e -> realizarCadastro());
        voltarButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void realizarCadastro() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String cep = cepField.getText().trim();
        String email = emailField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String senha = new String(senhaField.getPassword());
        String confirmarSenha = new String(confirmarSenhaField.getPassword());
        
        if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (*)", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            PessoaDAO.buscarPorNome(nome);
            // Se chegou aqui, o usuário não existe (buscarPorNome retorna null)
            
            Usuario novoUsuario = new Usuario(nome, cpf, cep, email, telefone, senha);
            PessoaDAO.salvar(novoUsuario);
            
            JOptionPane.showMessageDialog(this, "✅ Cadastro realizado com sucesso!");
            new LoginView();
            dispose();
            
        } catch (Exception e) {
            if (e.getMessage().contains("Found")) {
                JOptionPane.showMessageDialog(this, "Usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}