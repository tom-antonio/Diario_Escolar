package view;

import dao.DaoAluno;
import dao.DaoDisciplina;
import dao.DaoPeriodo;
import dao.DaoTurma;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Aluno;
import model.Disciplina;
import model.Nota;
import model.Periodo;
import model.Turma;

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
    private DaoAluno daoAluno;
    private DaoDisciplina daoDisciplina;
    private DaoPeriodo daoPeriodo;
    private DaoTurma daoTurma;

    public FormDiario() {
        notas = new ArrayList<>();
        daoAluno = new DaoAluno();
        daoDisciplina = new DaoDisciplina();
        daoPeriodo = new DaoPeriodo();
        daoTurma = new DaoTurma();
        setTitle("Diário de Notas");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        carregarAlunos();
        carregarDisciplinas();
        carregarPeriodos();
        carregarTurmas();
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

        cmbAluno = new JComboBox<>();
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

        cmbPeriodo = new JComboBox<>();
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

        cmbTurma = new JComboBox<>();
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

        cmbDisciplina = new JComboBox<>();
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

        btnAdicionarNota = new JButton("Adicionar Nota");
        btnAdicionarNota.setPreferredSize(new Dimension(200, 40));
        btnAdicionarNota.setFont(new Font("Arial", Font.BOLD, 12));
        btnAdicionarNota.setBackground(new Color(70, 130, 180));
        btnAdicionarNota.setForeground(Color.WHITE);
        btnAdicionarNota.setFocusPainted(false);
        //tornar o botão opaco e forçar a pintura do fundo
        btnAdicionarNota.setOpaque(true);
        btnAdicionarNota.setContentAreaFilled(true);
        btnAdicionarNota.setBorderPainted(false);
        btnAdicionarNota.addActionListener(e -> new FormAdicionarNota(this));

        btnRemoverNota = new JButton("Remover Nota");
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

    public void adicionarNotaNaTabela(String nota) {
        if (nota == null || nota.trim().isEmpty()) {
            return;
        }

        int novaColuna = modeloTabela.getColumnCount();
        String tituloColuna = "Nota " + (novaColuna + 1);
        modeloTabela.addColumn(tituloColuna);

        // Garante que há pelo menos uma linha
        if (modeloTabela.getRowCount() == 0) {
            modeloTabela.addRow(new Object[]{nota});
        } else {
            modeloTabela.setValueAt(nota, 0, novaColuna);
        }
    }

    private void atualizarTextoStatus() {
        if (togStatus.isSelected()) {
            togStatus.setText("Aprovado");
        } else {
            togStatus.setText("Reprovado");
        }
    }

    private void carregarAlunos() {
        List<Aluno> alunos = daoAluno.listarTodos();
        cmbAluno.removeAllItems();
        cmbAluno.addItem("Selecione um Aluno");
        for (Aluno aluno : alunos) {
            cmbAluno.addItem(aluno.getNome());
        }
    }

    private void carregarDisciplinas() {
        List<Disciplina> disciplinas = daoDisciplina.listarTodos();
        cmbDisciplina.removeAllItems();
        cmbDisciplina.addItem("Selecione uma disciplina");
        for (Disciplina disciplina : disciplinas) {
            cmbDisciplina.addItem(disciplina.getNome_disciplina());
        }
    }

    private void carregarPeriodos() {
        List<Periodo> periodos = daoPeriodo.listarTodos();
        cmbPeriodo.removeAllItems();
        cmbPeriodo.addItem("Selecione um Período");
        for (Periodo periodo : periodos) {
            cmbPeriodo.addItem(periodo.getNome_periodo());
        }
    }

    private void carregarTurmas() {
        List<Turma> turmas = daoTurma.listarTodos();
        cmbTurma.removeAllItems();
        cmbTurma.addItem("Selecione uma Turma");
        for (Turma turma : turmas) {
            cmbTurma.addItem(turma.getNome_turma());
        }
    }
}