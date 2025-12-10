package view;

import controller.TurmaController;
import java.awt.*;
import javax.swing.*;

public class FormTurma extends JFrame {

    private JTextField txtNome_turma;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private TurmaController turmaController;
    private Integer idTurmaAtual;

    public FormTurma() {
        setTitle("Cadastro de Turma");
        turmaController = new TurmaController(this);
        setSize(400, 150);
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

        //Campo Turma
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Turma:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtNome_turma = new JTextField(20);
        painelPrincipal.add(txtNome_turma, gbc);

        //Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> {
            String nomeTurma = txtNome_turma.getText().trim();

            String erro = turmaController.salvarTurma(nomeTurma);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Turma salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        });
        btnAlterar.addActionListener(e -> {
            if (idTurmaAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhuma turma selecionada para alterar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nomeTurma = txtNome_turma.getText().trim();

            String erro = turmaController.alterarTurma(idTurmaAtual, nomeTurma);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Turma alterada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        });
        btnExcluir.addActionListener(e -> {
            if (idTurmaAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhuma turma selecionada para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esta turma?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                return;
            }

            String erro = turmaController.excluirTurma(idTurmaAtual);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Turma excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        });
        btnPesquisar.addActionListener(e -> {
            String nomeTurma = JOptionPane.showInputDialog(this, "Digite o nome da turma:", "Pesquisar", JOptionPane.QUESTION_MESSAGE);

            if (nomeTurma == null || nomeTurma.trim().isEmpty()) {
                return;
            }

            model.Turma turma = turmaController.pesquisarTurma(nomeTurma);

            if (turma != null) {
                txtNome_turma.setText(turma.getNome_turma());
                idTurmaAtual = turma.getId();
                JOptionPane.showMessageDialog(this, "Turma encontrada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Turma não encontrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                limparCampos();
            }
        });

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal);
    }

    private void limparCampos() {
        txtNome_turma.setText("");
        idTurmaAtual = null;
        txtNome_turma.requestFocus();
    }
}