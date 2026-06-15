package view;

import dao.*;
import model.*;
import exception.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MenuBarbeiroView extends JFrame {
    private Barbeiro barbeiro;
    private JTabbedPane tabbedPane;
    private JTable usuariosTable, agendamentosTable, horariosTable;
    private DefaultTableModel usuariosModel, agendamentosModel, horariosModel;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public MenuBarbeiroView(Barbeiro barbeiro) {
        this.barbeiro = barbeiro;
        setTitle("Painel do Barbeiro - " + barbeiro.getNome());
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("📅 Gerenciar Horários", criarAbaHorarios());
        tabbedPane.addTab("👥 Gerenciar Usuários", criarAbaUsuarios());
        tabbedPane.addTab("✂️ Agendamentos", criarAbaAgendamentos());
        tabbedPane.addTab("👤 Meu Perfil", criarAbaPerfil());
        
        add(tabbedPane);
        carregarDados();
        setVisible(true);
    }
    
    private JPanel criarAbaHorarios() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Formulário para criar horários
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Configurar Horários Disponíveis"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Data:"), gbc);
        JTextField dataField = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(dataField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Hora Início (HH:mm):"), gbc);
        JTextField horaInicioField = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(horaInicioField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Hora Fim (HH:mm):"), gbc);
        JTextField horaFimField = new JTextField(10);
        gbc.gridx = 1;
        formPanel.add(horaFimField, gbc);
        
        JButton gerarButton = new JButton("Gerar Horários de 30 min");
        gerarButton.setBackground(new Color(46, 204, 113));
        gerarButton.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(gerarButton, gbc);
        
        // Tabela de horários
        String[] colunas = {"Horário", "Disponível"};
        horariosModel = new DefaultTableModel(colunas, 0);
        horariosTable = new JTable(horariosModel);
        JScrollPane scrollPane = new JScrollPane(horariosTable);
        
        gerarButton.addActionListener(e -> {
            try {
                String data = dataField.getText();
                String horaInicio = horaInicioField.getText();
                String horaFim = horaFimField.getText();
                
                LocalDateTime inicio = LocalDateTime.parse(data + " " + horaInicio, formatter);
                LocalDateTime fim = LocalDateTime.parse(data + " " + horaFim, formatter);
                
                gerarHorarios(inicio, fim);
                carregarHorarios();
                
                JOptionPane.showMessageDialog(this, "Horários gerados com sucesso!");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato inválido! Use dd/MM/yyyy e HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void gerarHorarios(LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime atual = inicio;
        while (atual.isBefore(fim)) {
            LocalDateTime slotFim = atual.plusMinutes(30);
            HorarioDisponivel horario = new HorarioDisponivel(barbeiro.getNome(), atual, slotFim, 30);
            try {
                HorarioDAO.salvar(horario);
            } catch (CadastroException e) {
                e.printStackTrace();
            }
            atual = slotFim;
        }
    }
    
    private JPanel criarAbaUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Botões de ação
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("➕ Adicionar Usuário");
        JButton removeButton = new JButton("❌ Remover Usuário");
        JButton refreshButton = new JButton("🔄 Atualizar");
        
        addButton.setBackground(new Color(52, 152, 219));
        addButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(231, 76, 60));
        removeButton.setForeground(Color.WHITE);
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        
        // Tabela de usuários
        String[] colunas = {"Nome", "CPF", "Email", "Telefone"};
        usuariosModel = new DefaultTableModel(colunas, 0);
        usuariosTable = new JTable(usuariosModel);
        JScrollPane scrollPane = new JScrollPane(usuariosTable);
        
        addButton.addActionListener(e -> adicionarUsuario());
        removeButton.addActionListener(e -> removerUsuario());
        refreshButton.addActionListener(e -> carregarUsuarios());
        
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel criarAbaAgendamentos() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] colunas = {"Cliente", "Data/Hora", "Status"};
        agendamentosModel = new DefaultTableModel(colunas, 0);
        agendamentosTable = new JTable(agendamentosModel);
        JScrollPane scrollPane = new JScrollPane(agendamentosTable);
        
        JButton cancelarButton = new JButton("❌ Cancelar Agendamento");
        cancelarButton.addActionListener(e -> cancelarAgendamento());
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(cancelarButton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarAbaPerfil() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("MEU PERFIL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        // Exibir informações
        String[][] info = {
            {"Nome:", barbeiro.getNome()},
            {"CPF:", barbeiro.getCpf()},
            {"CEP:", barbeiro.getCep()},
            {"Email:", barbeiro.getEmail()},
            {"Telefone:", barbeiro.getTelefone()},
            {"Especialidade:", barbeiro.getEspecialidade()}
        };
        
        for (int i = 0; i < info.length; i++) {
            gbc.gridy = i + 1;
            gbc.gridwidth = 1;
            gbc.gridx = 0;
            JLabel label = new JLabel(info[i][0]);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(label, gbc);
            
            gbc.gridx = 1;
            panel.add(new JLabel(info[i][1]), gbc);
        }
        
        JButton sairButton = new JButton("SAIR");
        sairButton.setBackground(new Color(231, 76, 60));
        sairButton.setForeground(Color.WHITE);
        gbc.gridy = info.length + 1;
        gbc.gridwidth = 2;
        panel.add(sairButton, gbc);
        
        sairButton.addActionListener(e -> {
            new LoginView();
            dispose();
        });
        
        return panel;
    }
    
    private void adicionarUsuario() {
        JTextField nomeField = new JTextField();
        JTextField cpfField = new JTextField();
        JTextField cepField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telefoneField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        
        Object[] fields = {
            "Nome:", nomeField,
            "CPF:", cpfField,
            "CEP:", cepField,
            "Email:", emailField,
            "Telefone:", telefoneField,
            "Senha:", senhaField
        };
        
        int option = JOptionPane.showConfirmDialog(this, fields, "Cadastrar Usuário", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Usuario novo = new Usuario(
                    nomeField.getText(), cpfField.getText(), cepField.getText(),
                    emailField.getText(), telefoneField.getText(), new String(senhaField.getPassword())
                );
                PessoaDAO.salvar(novo);
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                carregarUsuarios();
            } catch (CadastroException e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void removerUsuario() {
        int row = usuariosTable.getSelectedRow();
        if (row >= 0) {
            String nome = (String) usuariosModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Remover " + nome + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    PessoaDAO.remover(nome);
                    JOptionPane.showMessageDialog(this, "Usuário removido!");
                    carregarUsuarios();
                } catch (CadastroException e) {
                    JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário!");
        }
    }
    
    private void cancelarAgendamento() {
        int row = agendamentosTable.getSelectedRow();
        if (row >= 0) {
            String cliente = (String) agendamentosModel.getValueAt(row, 0);
            String dataHoraStr = (String) agendamentosModel.getValueAt(row, 1);
            try {
                LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);
                AgendamentoDAO.cancelarAgendamento(cliente, dataHora);
                HorarioDAO.atualizarDisponibilidade(barbeiro.getNome(), dataHora, true);
                JOptionPane.showMessageDialog(this, "Agendamento cancelado!");
                carregarAgendamentos();
                carregarHorarios();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void carregarUsuarios() {
        usuariosModel.setRowCount(0);
        try {
            for (Pessoa p : PessoaDAO.listarTodos()) {
                if (p.getTipo().equals("USUARIO")) {
                    usuariosModel.addRow(new Object[]{p.getNome(), p.getCpf(), p.getEmail(), p.getTelefone()});
                }
            }
        } catch (CadastroException e) {
            e.printStackTrace();
        }
    }
    
    private void carregarAgendamentos() {
        agendamentosModel.setRowCount(0);
        try {
            for (Agendamento a : AgendamentoDAO.buscarPorBarbeiro(barbeiro.getNome())) {
                agendamentosModel.addRow(new Object[]{a.getNomeUsuario(), a.getDataHoraFormatada(), a.getStatus()});
            }
        } catch (AgendamentoException e) {
            e.printStackTrace();
        }
    }
    
    private void carregarHorarios() {
        horariosModel.setRowCount(0);
        try {
            for (HorarioDisponivel h : HorarioDAO.buscarPorBarbeiro(barbeiro.getNome())) {
                horariosModel.addRow(new Object[]{h.getHorarioFormatado(), h.isDisponivel() ? "✅ Disponível" : "❌ Ocupado"});
            }
        } catch (CadastroException e) {
            e.printStackTrace();
        }
    }
    
    private void carregarDados() {
        carregarUsuarios();
        carregarAgendamentos();
        carregarHorarios();
    }
}