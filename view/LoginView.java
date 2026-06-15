package view;

import dao.PessoaDAO;
import model.Pessoa;
import model.Barbeiro;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton entrarButton;
    private JLabel cadastrarLink;

    // CONSTRUTOR: Configura a interface gráfica do login
    public LoginView() {
        setTitle("Sistema de Barbearia - Login");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(44, 62, 80), 0, getHeight(), new Color(52, 73, 94));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("✂️ SISTEMA DE BARBEARIA ✂️");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        mainPanel.add(Box.createVerticalStrut(30), gbc);
        
        JLabel userLabel = new JLabel("Usuário");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(userLabel, gbc);
        
        usuarioField = new JTextField(15);
        usuarioField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        mainPanel.add(usuarioField, gbc);
        
        JLabel passLabel = new JLabel("Senha");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passLabel, gbc);
        
        senhaField = new JPasswordField(15);
        senhaField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        mainPanel.add(senhaField, gbc);
        
        entrarButton = new JButton("Entrar");
        entrarButton.setFont(new Font("Arial", Font.BOLD, 14));
        entrarButton.setBackground(new Color(46, 204, 113));
        entrarButton.setForeground(Color.WHITE);
        entrarButton.setFocusPainted(false);
        entrarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(entrarButton, gbc);
        
        cadastrarLink = new JLabel("<HTML><U>Ainda não possui conta? Cadastrar</U></HTML>");
        cadastrarLink.setFont(new Font("Arial", Font.PLAIN, 12));
        cadastrarLink.setForeground(new Color(52, 152, 219));
        cadastrarLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 10, 10, 10);
        mainPanel.add(cadastrarLink, gbc);
        
        entrarButton.addActionListener(e -> realizarLogin());
        cadastrarLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirCadastro();
            }
        });
        
        getRootPane().setDefaultButton(entrarButton);
        add(mainPanel);
        setVisible(true);
    }
    
    private void realizarLogin() {
        String nome = usuarioField.getText().trim();
        String senha = new String(senhaField.getPassword());
        
        if (nome.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Pessoa pessoa = PessoaDAO.buscarPorNome(nome);
            if (pessoa != null && pessoa.getSenha().equals(senha)) {
                JOptionPane.showMessageDialog(this, "✅ Login realizado com sucesso!");
                
                // POLIMORFISMO: decide qual tela abrir baseado no tipo
                if (pessoa.getTipo().equals("BARBEIRO")) {
                    new MenuBarbeiroView((Barbeiro) pessoa);
                } else {
                    new MenuUsuarioView((Usuario) pessoa);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Usuário ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao acessar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirCadastro() {
        new CadastroView();
        dispose();
    }
}