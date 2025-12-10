package view;

import controller.PeriodoController;
import java.awt.*;
import javax.swing.*;

public class FormPeriodo extends JFrame {

    private JTextField txtNome_periodo;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private PeriodoController periodoController;
    private Integer idPeriodoAtual;
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(FormPeriodo.class.getName());

    public FormPeriodo() {
        LOG.info("Iniciando formulário de cadastro de período");
        setTitle("Cadastro de Período");
        periodoController = new PeriodoController(this);
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        LOG.info("Inicializando componentes do formulário de período");
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.WEST;

        //Campo Período
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Período:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtNome_periodo = new JTextField(20);
        painelPrincipal.add(txtNome_periodo, gbc);

        //Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> {
            LOG.info("Salvando novo período");
            String nomePeriodo = txtNome_periodo.getText().trim();

            String erro = periodoController.salvarPeriodo(nomePeriodo);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Período salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        });

        btnAlterar.addActionListener(e -> {
            LOG.info("Alterando período ID: " + idPeriodoAtual);
            if (idPeriodoAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum período selecionado para alterar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nomePeriodo = txtNome_periodo.getText().trim();

            String erro = periodoController.alterarPeriodo(idPeriodoAtual, nomePeriodo);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Período alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        });

        btnExcluir.addActionListener(e -> {
            LOG.info("Excluindo período ID: " + idPeriodoAtual);
            if (idPeriodoAtual == null) {
                JOptionPane.showMessageDialog(this, "Nenhum período selecionado para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este período?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                return;
            }

            String erro = periodoController.excluirPeriodo(idPeriodoAtual);

            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Período excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        });

        btnPesquisar.addActionListener(e -> {
            LOG.info("Pesquisando período");
            String idTexto = JOptionPane.showInputDialog(this, "Digite o ID do período:", "Pesquisar", JOptionPane.QUESTION_MESSAGE);

            if (idTexto == null || idTexto.trim().isEmpty()) {
                return;
            }

            try {
                int id = Integer.parseInt(idTexto.trim());
                
                model.Periodo periodo = periodoController.pesquisarPeriodo(id);

                if (periodo == null) {
                    JOptionPane.showMessageDialog(this, "Período não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    limparCampos();
                } else {
                    // Preencher os campos com os dados encontrados
                    txtNome_periodo.setText(periodo.getNome_periodo());
                    idPeriodoAtual = periodo.getId();
                    LOG.info("Período encontrado e preenchido: " + periodo.getNome_periodo());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
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
        LOG.info("Limpando campos do formulário de período");
        txtNome_periodo.setText("");
        idPeriodoAtual = null;
        txtNome_periodo.requestFocus();
    }
}