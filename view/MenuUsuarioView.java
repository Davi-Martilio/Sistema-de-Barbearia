package view;

import dao.AgendamentoDAO;
import model.Agendamento;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MenuUsuarioView extends JFrame {
    private Usuario usuario;
    private JTabbedPane tabbedPane;
    private JTextField barbeiroField, dataHoraField;
    private JTable agendamentosTable;
    private DefaultTableModel tableModel;
    
    public MenuUsuarioView(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Sistema de Barbearia - " + usuario.getNome());
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Criar abas
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("✂️ Agendar Horário", criarAbaAgendar());
        tabbedPane.addTab("📅 Meus Agendamentos", criarAbaAgendamentos());
        tabbedPane.addTab("👤 Meu Perfil", criarAbaPerfil());
        
        add(tabbedPane);
        setVisible(true);
        
        // Carregar agendamentos ao iniciar
        carregarAgendamentos();
    }
    
    private JPanel criarAbaAgendar() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Título
        JLabel titleLabel = new JLabel("AGENDAR HORÁRIO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        // Barbeiro
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel barbeiroLabel = new JLabel("Nome do Barbeiro:");
        barbeiroLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(barbeiroLabel, gbc);
        
        barbeiroField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(barbeiroField, gbc);
        
        // Data/Hora
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel dataLabel = new JLabel("Data e Hora (dd/MM/yyyy HH:mm):");
        dataLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(dataLabel, gbc);
        
        dataHoraField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(dataHoraField, gbc);
        
        // Botão Agendar
        JButton agendarButton = new JButton("AGENDAR");
        agendarButton.setBackground(new Color(46, 204, 113));
        agendarButton.setForeground(Color.WHITE);
        agendarButton.setFont(new Font("Arial", Font.BOLD, 14));
        agendarButton.setFocusPainted(false);
        agendarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(agendarButton, gbc);
        
        // Informação
        JLabel infoLabel = new JLabel("💡 Exemplo: 25/12/2024 14:30");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setForeground(Color.GRAY);
        gbc.gridy = 4;
        panel.add(infoLabel, gbc);
        
        // Ação do botão
        agendarButton.addActionListener(e -> agendarHorario());
        
        return panel;
    }
    
    private JPanel criarAbaAgendamentos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Tabela de agendamentos
        String[] colunas = {"Barbeiro", "Data e Hora"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        agendamentosTable = new JTable(tableModel);
        agendamentosTable.setFont(new Font("Arial", Font.PLAIN, 12));
        agendamentosTable.setRowHeight(25);
        agendamentosTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        
        JScrollPane scrollPane = new JScrollPane(agendamentosTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Botão Atualizar
        JButton atualizarButton = new JButton("🔄 Atualizar Lista");
        atualizarButton.setBackground(new Color(52, 152, 219));
        atualizarButton.setForeground(Color.WHITE);
        atualizarButton.setFocusPainted(false);
        atualizarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        atualizarButton.addActionListener(e -> carregarAgendamentos());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(atualizarButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarAbaPerfil() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Título
        JLabel titleLabel = new JLabel("MEU PERFIL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        // Usuário
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(userLabel, gbc);
        
        JLabel userValueLabel = new JLabel(usuario.getNome());
        userValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(userValueLabel, gbc);
        
        // Senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Senha:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(passLabel, gbc);
        
        JLabel passValueLabel = new JLabel(usuario.getSenha());
        passValueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(passValueLabel, gbc);
        
        // Botão Sair
        JButton sairButton = new JButton("SAIR DO SISTEMA");
        sairButton.setBackground(new Color(231, 76, 60));
        sairButton.setForeground(Color.WHITE);
        sairButton.setFont(new Font("Arial", Font.BOLD, 14));
        sairButton.setFocusPainted(false);
        sairButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(sairButton, gbc);
        
        sairButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginView();
                dispose();
            }
        });
        
        return panel;
    }
    
    private void agendarHorario() {
        String barbeiro = barbeiroField.getText().trim();
        String dataHora = dataHoraField.getText().trim();
        
        if (barbeiro.isEmpty() || dataHora.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            boolean disponivel = AgendamentoDAO.horarioDisponivel(barbeiro, dataHora);
            if (!disponivel) {
                JOptionPane.showMessageDialog(this, "❌ Horário já ocupado para este barbeiro!", "Indisponível", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Agendamento novo = new Agendamento(usuario.getNome(), barbeiro, dataHora);
            AgendamentoDAO.salvar(novo);
            
            JOptionPane.showMessageDialog(this, "✅ Horário agendado com sucesso!");
            barbeiroField.setText("");
            dataHoraField.setText("");
            carregarAgendamentos(); // Atualiza a lista automaticamente
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao agendar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void carregarAgendamentos() {
        try {
            List<Agendamento> agendamentos = AgendamentoDAO.buscarPorUsuario(usuario.getNome());
            tableModel.setRowCount(0);
            
            for (Agendamento a : agendamentos) {
                tableModel.addRow(new Object[]{a.getNomeBarbeiro(), a.getDataHora()});
            }
            
            if (agendamentos.isEmpty()) {
                tableModel.addRow(new Object[]{"Nenhum agendamento encontrado", ""});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar agendamentos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}