package view;

import java.awt.*;
import javax.swing.*;

public class FormAdicionarNota extends JFrame {

    private JComboBox<String> cmbNotas;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private FormDiario formDiario;

    public FormAdicionarNota(FormDiario formDiario) {
        this.formDiario = formDiario;
        setTitle("Adicionar Nota");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.WEST;

        // Label Nota
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Selecionar Nota:"), gbc);

        // ComboBox Notas
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        DefaultComboBoxModel<String> modelNota = new DefaultComboBoxModel<>();
        modelNota.addElement("Selecione uma Nota");
        cmbNotas = new JComboBox<>(modelNota);
        painelPrincipal.add(cmbNotas, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> cancelar());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        painelPrincipal.add(painelBotoes, gbc);

        add(painelPrincipal);
    }

    private void salvar() {
        String notaSelecionada = (String) cmbNotas.getSelectedItem();
        if (notaSelecionada == null || notaSelecionada.startsWith("Selecione")) {
            JOptionPane.showMessageDialog(this, "Selecione uma nota.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (formDiario != null) {
            formDiario.adicionarNotaNaTabela(notaSelecionada);
        }

        dispose();
    }

    private void cancelar() {
        dispose();
    }
}
