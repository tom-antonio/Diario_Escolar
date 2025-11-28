package view;

import java.awt.*;
import javax.swing.*;

public class FormDiario extends JFrame {

    private JToggleButton togStatus;
    private JComboBox<String> cmbAluno;
    private JComboBox<String> cmbPeriodo;
    private JComboBox<String> cmbTurma;
    private JComboBox<String> cmbDisciplina;
    private JComboBox<String> cmbNota;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;

    public FormDiario() {
        setTitle("Diário de Notas");
        setSize(400, 350);
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

        //Campo Nota
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Nota:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        DefaultComboBoxModel<String> modelNota = new DefaultComboBoxModel<>();
        modelNota.addElement("Selecione uma Nota");
        cmbNota = new JComboBox<>(modelNota);
        painelPrincipal.add(cmbNota, gbc);

        //Campo Status
        gbc.gridx = 0;
        gbc.gridy = 6;
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
        gbc.gridy = 10;
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
