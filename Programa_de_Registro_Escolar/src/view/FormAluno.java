package view;

import controller.AlunoController;
import java.awt.*;
import javax.swing.*;

public class FormAluno extends JFrame {

    private JTextField txtNome;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtMatricula;
    private JTextField txtNome_pai;
    private JTextField txtNome_mae;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private AlunoController alunoController;
    private Integer idAlunoAtual;

    public FormAluno() {
        alunoController = new AlunoController(this);
        setTitle("Cadastro de Aluno");
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

        //Campo Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtNome = new JTextField(20);
        painelPrincipal.add(txtNome, gbc);

        //Campo Endereço
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Endereço:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtEndereco = new JTextField(20);
        painelPrincipal.add(txtEndereco, gbc);

        //Campo Telefone
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtTelefone = new JTextField(20);
        painelPrincipal.add(txtTelefone, gbc);

        //Campo Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtEmail = new JTextField(20);
        painelPrincipal.add(txtEmail, gbc);

        //Campo Matrícula
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Matrícula:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtMatricula = new JTextField(20);
        painelPrincipal.add(txtMatricula, gbc);

        //Campo Nome do Pai
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Nome do Pai:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtNome_pai = new JTextField(20);
        painelPrincipal.add(txtNome_pai, gbc);

        //Campo Nome da Mãe
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Nome da Mãe:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtNome_mae = new JTextField(20);
        painelPrincipal.add(txtNome_mae, gbc);

        //Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String endereco = txtEndereco.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String matricula = txtMatricula.getText().trim();
            String nomePai = txtNome_pai.getText().trim();
            String nomeMae = txtNome_mae.getText().trim();

            String erro = alunoController.salvarAluno(nome, endereco, telefone, email, matricula, nomePai, nomeMae);

            if (erro == null) {
                JOptionPane.showMessageDialog(this,
                        "Aluno salvo com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this,
                        erro,
                        "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        btnAlterar.addActionListener(e -> {
            if (idAlunoAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum aluno selecionado para alterar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nome = txtNome.getText().trim();
            String endereco = txtEndereco.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String matricula = txtMatricula.getText().trim();
            String nomePai = txtNome_pai.getText().trim();
            String nomeMae = txtNome_mae.getText().trim();

            String erro = alunoController.alterarAluno(idAlunoAtual, nome, endereco, telefone, email, matricula, nomePai, nomeMae);

            if (erro == null) {
                JOptionPane.showMessageDialog(this, "Aluno alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnExcluir.addActionListener(e -> {
            if (idAlunoAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum aluno selecionado para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este aluno?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                return;
            }

            String erro = alunoController.excluirAluno(idAlunoAtual);

            if (erro == null) {
                JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPesquisar.addActionListener(e -> {
            String nomeTexto = JOptionPane.showInputDialog(this, "Digite o nome do aluno:", "Pesquisar", JOptionPane.QUESTION_MESSAGE);

            if (nomeTexto == null || nomeTexto.trim().isEmpty()) {
                return;
            }

            model.Aluno aluno = alunoController.pesquisarAluno(nomeTexto.trim());

            if (aluno == null) {
                JOptionPane.showMessageDialog(this, "Aluno não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                limparCampos();
            } else {
                idAlunoAtual = aluno.getId();
                txtNome.setText(aluno.getNome());
                txtEndereco.setText(aluno.getEndereco());
                txtTelefone.setText(aluno.getTelefone());
                txtEmail.setText(aluno.getEmail());
                txtMatricula.setText(String.valueOf(aluno.getMatricula()));
                txtNome_pai.setText(aluno.getNome_pai());
                txtNome_mae.setText(aluno.getNome_mae());
            }
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

    private void limparCampos() {
        txtNome.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        txtNome_pai.setText("");
        txtNome_mae.setText("");
        idAlunoAtual = null;
        txtNome.requestFocus();
    }

}