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

    public FormPeriodo() {
        setTitle("Cadastro de Período");
        periodoController = new PeriodoController(this);
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
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal);
    }

    private void limparCampos() {
        txtNome_periodo.setText("");
        txtNome_periodo.requestFocus();
    }
}
