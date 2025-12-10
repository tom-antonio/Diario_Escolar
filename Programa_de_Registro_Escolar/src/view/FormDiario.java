package view;

import controller.DiarioController;
import dao.DaoAluno;
import dao.DaoDisciplina;
import dao.DaoPeriodo;
import dao.DaoTurma;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import model.Aluno;
import model.Disciplina;
import model.Periodo;
import model.Turma;

public class FormDiario extends JFrame {

    private JComboBox<String> cmbAluno;
    private JComboBox<String> cmbPeriodo;
    private JComboBox<String> cmbTurma;
    private JComboBox<String> cmbDisciplina;
    private JList<String> listNotas;
    private DefaultListModel<String> modeloLista;
    private JToggleButton togStatus;
    private JButton btnAdicionarNota;
    private JButton btnRemoverNota;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private List<Double> notas;
    private DaoAluno daoAluno;
    private DaoDisciplina daoDisciplina;
    private DaoPeriodo daoPeriodo;
    private DaoTurma daoTurma;
    private HashMap<String, Integer> alunosMap;
    private HashMap<String, Integer> disciplinasMap;
    private HashMap<String, Integer> periodosMap;
    private HashMap<String, Integer> turmasMap;
    private DiarioController diarioController;
    private String alunoSelecionado;
    private String disciplinaSelecionada;
    private String periodSelecionado;
    private String turmaSelecionada;

    public FormDiario() {
        notas = new ArrayList<>();
        daoAluno = new DaoAluno();
        daoDisciplina = new DaoDisciplina();
        daoPeriodo = new DaoPeriodo();
        daoTurma = new DaoTurma();
        diarioController = new DiarioController();
        
        alunosMap = new HashMap<>();
        disciplinasMap = new HashMap<>();
        periodosMap = new HashMap<>();
        turmasMap = new HashMap<>();
        
        setTitle("Diário de Notas");
        setSize(500, 500);
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
        
        // Aluno
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Aluno:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        cmbAluno = new JComboBox<>();
        cmbAluno.addActionListener(e -> atualizarSelecao());
        painelPrincipal.add(cmbAluno, gbc);

        // Período
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Período:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        cmbPeriodo = new JComboBox<>();
        cmbPeriodo.addActionListener(e -> atualizarSelecao());
        painelPrincipal.add(cmbPeriodo, gbc);

        // Turma
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Turma:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        cmbTurma = new JComboBox<>();
        cmbTurma.addActionListener(e -> atualizarSelecao());
        painelPrincipal.add(cmbTurma, gbc);

        // Disciplina
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Disciplina:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        cmbDisciplina = new JComboBox<>();
        cmbDisciplina.addActionListener(e -> atualizarSelecao());
        painelPrincipal.add(cmbDisciplina, gbc);
        
        // Notas
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Notas:"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        
        modeloLista = new DefaultListModel<>();
        listNotas = new JList<>(modeloLista);
        listNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listNotas.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(listNotas);
        scrollPane.setPreferredSize(new Dimension(600, 150));
        painelPrincipal.add(scrollPane, gbc);
        
        // Botões de notas
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        gbc.weightx = 1;
        
        btnAdicionarNota = new JButton("Adicionar Nota");
        btnAdicionarNota.setPreferredSize(new Dimension(200, 40));
        btnAdicionarNota.setFont(new Font("Arial", Font.BOLD, 12));
        btnAdicionarNota.setBackground(new Color(70, 130, 180));
        btnAdicionarNota.setForeground(Color.WHITE);
        btnAdicionarNota.setFocusPainted(false);
        btnAdicionarNota.setOpaque(true);
        btnAdicionarNota.setContentAreaFilled(true);
        btnAdicionarNota.setBorderPainted(false);
        btnAdicionarNota.addActionListener(e -> adicionarNota());

        btnRemoverNota = new JButton("Remover Nota");
        btnRemoverNota.setPreferredSize(new Dimension(200, 40));
        btnRemoverNota.setFont(new Font("Arial", Font.BOLD, 11));
        btnRemoverNota.setBackground(new Color(220, 20, 60));
        btnRemoverNota.setForeground(Color.WHITE);
        btnRemoverNota.setFocusPainted(false);
        btnRemoverNota.setOpaque(true);
        btnRemoverNota.setContentAreaFilled(true);
        btnRemoverNota.setBorderPainted(false);
        btnRemoverNota.addActionListener(e -> removerNota());

        JPanel painelBotoesNotas = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        painelBotoesNotas.add(btnAdicionarNota);
        painelBotoesNotas.add(btnRemoverNota);
        
        painelPrincipal.add(painelBotoesNotas, gbc);
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        //Campo Aprovado/Reprovado
        togStatus = new JToggleButton();
        togStatus.setSelected(true);
        atualizarTextoStatus();
        togStatus.addItemListener(e -> atualizarTextoStatus());
        togStatus.setEnabled(false);
        painelPrincipal.add(togStatus, gbc);

        
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> salvar());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnPesquisar.addActionListener(e -> pesquisar());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal);
    }
    
    private void atualizarSelecao() {
        alunoSelecionado = (String) cmbAluno.getSelectedItem();
        periodSelecionado = (String) cmbPeriodo.getSelectedItem();
        turmaSelecionada = (String) cmbTurma.getSelectedItem();
        disciplinaSelecionada = (String) cmbDisciplina.getSelectedItem();
        
        // Valida se algum está em "Selecione"
        if (alunoSelecionado.startsWith("Selecione") ||
            periodSelecionado.startsWith("Selecione") ||
            turmaSelecionada.startsWith("Selecione") ||
            disciplinaSelecionada.startsWith("Selecione")) {
            
            limparCampos();
            return;
        }

        notas.clear();
        atualizarExibicao();
    }

    private void atualizarExibicao() {
        diarioController.atualizarExibicao(notas, modeloLista, togStatus);
    }
    
    private void limparCampos() {
        notas.clear();
        modeloLista.clear();
        togStatus.setSelected(true);
        diarioController.atualizarTextoStatus(togStatus);
    }
    
    private void atualizarTextoStatus() {
        diarioController.atualizarTextoStatus(togStatus);
    }
    
    private void salvar() {

    }
    
    private void alterar() {
  
    }
    
    private void excluir() {

    }
    
    private void pesquisar() {

    }

    private void carregarAlunos() {
        List<Aluno> alunos = daoAluno.listarTodos();
        cmbAluno.removeAllItems();
        cmbAluno.addItem("Selecione um Aluno");
        
        for (Aluno aluno : alunos) {
            String nome = aluno.getNome();
            cmbAluno.addItem(nome);
            alunosMap.put(nome, aluno.getId());
        }
    }

    private void carregarDisciplinas() {
        List<Disciplina> disciplinas = daoDisciplina.listarTodos();
        cmbDisciplina.removeAllItems();
        cmbDisciplina.addItem("Selecione uma disciplina");
        
        for (Disciplina disciplina : disciplinas) {
            String nome = disciplina.getNome_disciplina();
            cmbDisciplina.addItem(nome);
            disciplinasMap.put(nome, disciplina.getId());
        }
    }

    private void carregarPeriodos() {
        List<Periodo> periodos = daoPeriodo.listarTodos();
        cmbPeriodo.removeAllItems();
        cmbPeriodo.addItem("Selecione um Período");
        
        for (Periodo periodo : periodos) {
            String nome = periodo.getNome_periodo();
            cmbPeriodo.addItem(nome);
            periodosMap.put(nome, periodo.getId());
        }
    }

    private void carregarTurmas() {
        List<Turma> turmas = daoTurma.listarTodos();
        cmbTurma.removeAllItems();
        cmbTurma.addItem("Selecione uma Turma");
        
        for (Turma turma : turmas) {
            String nome = turma.getNome_turma();
            cmbTurma.addItem(nome);
            turmasMap.put(nome, turma.getId());
        }
    }

    private void adicionarNota() {
        String input = JOptionPane.showInputDialog(
            this,
            "Adicionar Nota",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (input == null) return;
        
        try {
            double nota = Double.parseDouble(input.trim());
            
            if (nota < 0 || nota > 10) {
                JOptionPane.showMessageDialog(
                    this,
                    "Nota deve estar entre 0 e 10",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            notas.add(nota);
            atualizarExibicao();
        
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Valor inválido! Digite um número",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void removerNota() {
        int indiceSelecionado = listNotas.getSelectedIndex();
        
        if (indiceSelecionado == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Selecione uma nota para remover",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        double notaRemovida = notas.remove(indiceSelecionado);
        atualizarExibicao();
    
    }
}