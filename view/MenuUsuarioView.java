package view;

import dao.*;
import model.*;
import exception.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class MenuUsuarioView extends JFrame {
    private Usuario usuario;
    private JTabbedPane tabbedPane;
    private JTable horariosTable, agendamentosTable;
    private DefaultTableModel horariosModel, agendamentosModel;
    private JComboBox<String> barbeiroCombo;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private List<HorarioDisponivel> horariosDisponiveisCache = new ArrayList<>();
    
    public MenuUsuarioView(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Sistema de Barbearia - " + usuario.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("✂️ Agendar Horário", criarAbaAgendar());
        tabbedPane.addTab("📅 Meus Agendamentos", criarAbaAgendamentos());
        tabbedPane.addTab("👤 Meu Perfil", criarAbaPerfil());
        
        add(tabbedPane);
        carregarBarbeiros();
        carregarAgendamentos();
        setVisible(true);
    }
    
    private JPanel criarAbaAgendar() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Selecione o Barbeiro:"));
        barbeiroCombo = new JComboBox<>();
        barbeiroCombo.addActionListener(e -> carregarHorarios());
        topPanel.add(barbeiroCombo);
        
        String[] colunas = {"Horário Disponível"};
        horariosModel = new DefaultTableModel(colunas, 0);
        horariosTable = new JTable(horariosModel);
        JScrollPane scrollPane = new JScrollPane(horariosTable);
        
        JButton agendarButton = new JButton("Agendar Horário Selecionado");
        agendarButton.setBackground(new Color(46, 204, 113));
        agendarButton.setForeground(Color.WHITE);
        agendarButton.setFont(new Font("Arial", Font.BOLD, 14));
        agendarButton.addActionListener(e -> agendarHorario());
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(agendarButton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel criarAbaAgendamentos() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] colunas = {"Barbeiro", "Data/Hora", "Status"};
        agendamentosModel = new DefaultTableModel(colunas, 0);
        agendamentosTable = new JTable(agendamentosModel);
        JScrollPane scrollPane = new JScrollPane(agendamentosTable);
        
        JButton cancelarButton = new JButton("Cancelar Agendamento Selecionado");
        cancelarButton.setBackground(new Color(231, 76, 60));
        cancelarButton.setForeground(Color.WHITE);
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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        String[][] info = {
            {"Nome:", usuario.getNome()},
            {"CPF:", usuario.getCpf()},
            {"CEP:", usuario.getCep()},
            {"Email:", usuario.getEmail()},
            {"Telefone:", usuario.getTelefone()},
            {"Senha:", usuario.getSenha()}
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
        
        JButton sairButton = new JButton("SAIR DO SISTEMA");
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
    
    private void carregarBarbeiros() {
        barbeiroCombo.removeAllItems();
        try {
            for (Pessoa p : PessoaDAO.listarTodos()) {
                if (p.getTipo().equals("BARBEIRO")) {
                    barbeiroCombo.addItem(p.getNome());
                }
            }
            if (barbeiroCombo.getItemCount() > 0) {
                carregarHorarios();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar barbeiros: " + e.getMessage());
        }
    }
    
    private void carregarHorarios() {
        horariosModel.setRowCount(0);
        horariosDisponiveisCache.clear();
        
        String barbeiroSelecionado = (String) barbeiroCombo.getSelectedItem();
        if (barbeiroSelecionado == null) return;
        
        try {
            List<HorarioDisponivel> horarios = HorarioDAO.buscarPorBarbeiro(barbeiroSelecionado);
            for (HorarioDisponivel h : horarios) {
                horariosModel.addRow(new Object[]{h.getHorarioFormatado()});
                horariosDisponiveisCache.add(h);
            }
            if (horarios.isEmpty()) {
                horariosModel.addRow(new Object[]{"Nenhum horário disponível para este barbeiro"});
            }
        } catch (CadastroException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar horários: " + e.getMessage());
        }
    }
    
    private void carregarAgendamentos() {
        agendamentosModel.setRowCount(0);
        try {
            List<Agendamento> agendamentos = AgendamentoDAO.buscarPorUsuario(usuario.getNome());
            for (Agendamento a : agendamentos) {
                agendamentosModel.addRow(new Object[]{
                    a.getNomeBarbeiro(), 
                    a.getDataHoraFormatada(), 
                    a.getStatus()
                });
            }
            if (agendamentos.isEmpty()) {
                agendamentosModel.addRow(new Object[]{"Nenhum agendamento encontrado", "", ""});
            }
        } catch (AgendamentoException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar agendamentos: " + e.getMessage());
        }
    }
    
    private void agendarHorario() {
        int selectedRow = horariosTable.getSelectedRow();
        if (selectedRow < 0 || selectedRow >= horariosDisponiveisCache.size()) {
            JOptionPane.showMessageDialog(this, "Selecione um horário na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        HorarioDisponivel horario = horariosDisponiveisCache.get(selectedRow);
        String barbeiroNome = (String) barbeiroCombo.getSelectedItem();
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Confirmar agendamento com " + barbeiroNome + "?\n" + horario.getHorarioFormatado(),
            "Confirmar Agendamento", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Agendamento novo = new Agendamento(usuario.getNome(), barbeiroNome, horario.getDataHoraInicio());
                AgendamentoDAO.salvar(novo);
                HorarioDAO.atualizarDisponibilidade(barbeiroNome, horario.getDataHoraInicio(), false);
                
                JOptionPane.showMessageDialog(this, "✅ Horário agendado com sucesso!");
                carregarHorarios();
                carregarAgendamentos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao agendar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cancelarAgendamento() {
        int selectedRow = agendamentosTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String barbeiro = (String) agendamentosModel.getValueAt(selectedRow, 0);
        String dataHoraStr = (String) agendamentosModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Cancelar agendamento com " + barbeiro + " em " + dataHoraStr + "?",
            "Confirmar Cancelamento", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr, formatter);
                AgendamentoDAO.cancelarAgendamento(usuario.getNome(), dataHora);
                HorarioDAO.atualizarDisponibilidade(barbeiro, dataHora, true);
                
                JOptionPane.showMessageDialog(this, "✅ Agendamento cancelado!");
                carregarHorarios();
                carregarAgendamentos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao cancelar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}