package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Nota;

public class FormDiario extends JFrame {

    private JToggleButton togStatus;
    private JComboBox<String> cmbAluno;
    private JComboBox<String> cmbPeriodo;
    private JComboBox<String> cmbTurma;
    private JComboBox<String> cmbDisciplina;
    private JTable tabelaNotas;
    private DefaultTableModel modeloTabela;
    private JButton btnAdicionarNota;
    private JButton btnRemoverNota;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private List<Nota> notas;

    public FormDiario() {
        notas = new ArrayList<>();
        setTitle("Diário de Notas");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.WEST;

        //Campo Aluno
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Aluno:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        DefaultComboBoxModel<String> modelAluno = new DefaultComboBoxModel<>();
        modelAluno.addElement("Selecione um Aluno");
        cmbAluno = new JComboBox<>(modelAluno);
        painelPrincipal.add(cmbAluno, gbc);

        //Campo Período
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Período:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        DefaultComboBoxModel<String> modelPeriodo = new DefaultComboBoxModel<>();
        modelPeriodo.addElement("Selecione um Período");
        cmbPeriodo = new JComboBox<>(modelPeriodo);
        painelPrincipal.add(cmbPeriodo, gbc);

        //Campo Turma
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Turma:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        DefaultComboBoxModel<String> modelTurma = new DefaultComboBoxModel<>();
        modelTurma.addElement("Selecione uma Turma");
        cmbTurma = new JComboBox<>(modelTurma);
        painelPrincipal.add(cmbTurma, gbc);

        //Campo Disciplina
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Disciplina:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        DefaultComboBoxModel<String> modelDisciplina = new DefaultComboBoxModel<>();
        modelDisciplina.addElement("Selecione uma Disciplina");
        cmbDisciplina = new JComboBox<>(modelDisciplina);
        painelPrincipal.add(cmbDisciplina, gbc);

        //Tabela de Notas
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        modeloTabela = new DefaultTableModel(new Object[][]{{}}, new Object[]{}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaNotas = new JTable(modeloTabela);
        tabelaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaNotas.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(tabelaNotas);
        scrollPane.setPreferredSize(new Dimension(600, 80));
        painelPrincipal.add(scrollPane, gbc);

        //Botões de gerenciamento de notas
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 0;

        JButton btnAdicionarNota = new JButton("Adicionar Nota");
        btnAdicionarNota.setPreferredSize(new Dimension(200, 40));
        btnAdicionarNota.setFont(new Font("Arial", Font.BOLD, 12));
        btnAdicionarNota.setBackground(new Color(70, 130, 180));
        btnAdicionarNota.setForeground(Color.WHITE);
        btnAdicionarNota.setFocusPainted(false);
        //tornar o botão opaco e forçar a pintura do fundo
        btnAdicionarNota.setOpaque(true);
        btnAdicionarNota.setContentAreaFilled(true);
        btnAdicionarNota.setBorderPainted(false);
        btnAdicionarNota.addActionListener(e -> new FormAdicionarNota());

        JButton btnRemoverNota = new JButton("Remover Nota");
        btnRemoverNota.setPreferredSize(new Dimension(200, 40));
        btnRemoverNota.setFont(new Font("Arial", Font.BOLD, 12));
        btnRemoverNota.setBackground(new Color(220, 20, 160));
        btnRemoverNota.setForeground(Color.WHITE);
        btnRemoverNota.setFocusPainted(false);
        //tornar o botão opaco e forçar a pintura do fundo
        btnRemoverNota.setOpaque(true);
        btnRemoverNota.setContentAreaFilled(true);
        btnRemoverNota.setBorderPainted(false);

        JPanel painelBotoesNotas = new JPanel(new FlowLayout());
        painelBotoesNotas.add(btnAdicionarNota);
        painelBotoesNotas.add(btnRemoverNota);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoesNotas, gbc);

        //Campo Status
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        togStatus = new JToggleButton();
        togStatus.setSelected(true);
        atualizarTextoStatus();
        togStatus.addItemListener(e -> atualizarTextoStatus());
        painelPrincipal.add(togStatus, gbc);


        //Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> {
        
        });
        btnAlterar.addActionListener(e -> {
        
        });
        btnExcluir.addActionListener(e -> {
        
        });
        btnPesquisar.addActionListener(e -> {

        });

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal);
    }

    private void atualizarTextoStatus() {
        if (togStatus.isSelected()) {
            togStatus.setText("Aprovado");
        } else {
            togStatus.setText("Reprovado");
        }
    }
}