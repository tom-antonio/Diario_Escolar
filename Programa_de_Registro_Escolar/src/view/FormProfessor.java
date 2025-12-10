package view;

import controller.ProfessorController;
import java.awt.*;
import javax.swing.*;

public class FormProfessor extends JFrame {

    private JTextField txtNome;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtMatricula;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private ProfessorController professorController;
    private Integer idProfessorAtual;
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(FormProfessor.class.getName());

    public FormProfessor() {
        LOG.info("Iniciando formulário de cadastro de professor");
        professorController = new ProfessorController(this);
        setTitle("Cadastro de Professor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        LOG.info("Inicializando componentes do formulário de professor");
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

        //Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> {
            LOG.info("Salvando novo professor");
            String nome = txtNome.getText().trim();
            String endereco = txtEndereco.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String matricula = txtMatricula.getText().trim();

            String erro = professorController.salvarProfessor(nome, endereco, telefone, email, matricula);

            if (erro == null) {
                JOptionPane.showMessageDialog(this,
                        "Professor salvo com sucesso!",
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
            LOG.info("Alterando professor ID: " + idProfessorAtual);
            if (idProfessorAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum professor selecionado para alterar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nome = txtNome.getText().trim();
            String endereco = txtEndereco.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String matricula = txtMatricula.getText().trim();

            String erro = professorController.alterarProfessor(idProfessorAtual, nome, endereco, telefone, email, matricula);

            if (erro == null) {
                JOptionPane.showMessageDialog(this, "Professor alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            LOG.info("Excluindo professor ID: " + idProfessorAtual);
            if (idProfessorAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum professor selecionado para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este professor?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                return;
            }

            String erro = professorController.excluirProfessor(idProfessorAtual);

            if (erro == null) {
                JOptionPane.showMessageDialog(this, "Professor excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPesquisar.addActionListener(e -> {
            LOG.info("Pesquisando professor");
            String nomeTexto = JOptionPane.showInputDialog(this, "Digite o nome do professor:", "Pesquisar", JOptionPane.QUESTION_MESSAGE);

            if (nomeTexto == null || nomeTexto.trim().isEmpty()) {
                return;
            }

            model.Professor professor = professorController.pesquisarProfessorPorNome(nomeTexto.trim());

            if (professor == null) {
                JOptionPane.showMessageDialog(this, "Professor não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                limparCampos();
            } else {
                idProfessorAtual = professor.getId();
                txtNome.setText(professor.getNome());
                txtEndereco.setText(professor.getEndereco());
                txtTelefone.setText(professor.getTelefone());
                txtEmail.setText(professor.getEmail());
                txtMatricula.setText(String.valueOf(professor.getMatricula()));
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
        LOG.info("Limpando campos do formulário de professor");
        txtNome.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtMatricula.setText("");
        idProfessorAtual = null;
        txtNome.requestFocus();
    }
}
