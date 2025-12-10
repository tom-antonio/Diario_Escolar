package view;

import controller.DisciplinaController;
import dao.DaoProfessor;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import model.Professor;

public class FormDisciplina extends JFrame {

    private JTextField txtNome_disciplina;
    private JComboBox<String> cmbProfessor;
    private JButton btnSalvar;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnPesquisar;
    private DaoProfessor daoProfessor;
    private DisciplinaController disciplinaController;
    private Map<String, Integer> professoresMap;
    private Integer idDisciplinaAtual;

    public FormDisciplina() {
        disciplinaController = new DisciplinaController(this);
        daoProfessor = new DaoProfessor();
        professoresMap = new HashMap<>();
        setTitle("Cadastro de Disciplina");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        carregarProfessor();
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
        painelPrincipal.add(new JLabel("Disciplina:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        txtNome_disciplina = new JTextField(20);
        painelPrincipal.add(txtNome_disciplina, gbc);

        //Campo Professor
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelPrincipal.add(new JLabel("Professor:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        cmbProfessor = new JComboBox<>();
        painelPrincipal.add(cmbProfessor, gbc);


        //Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnSalvar = new JButton("Salvar");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnPesquisar = new JButton("Pesquisar");

        btnSalvar.addActionListener(e -> {
            String nomeDisciplina = txtNome_disciplina.getText().trim();
            String nomeProfessor = (String) cmbProfessor.getSelectedItem();
            int idProfessor = professoresMap.getOrDefault(nomeProfessor, -1);

            String erro = disciplinaController.salvarDisciplina(nomeDisciplina, nomeProfessor, idProfessor);

            if (erro == null) {
                JOptionPane.showMessageDialog(this,
                        "Disciplina salva com sucesso!",
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
            if (idDisciplinaAtual == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecione uma disciplina para alterar.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nomeDisciplina = txtNome_disciplina.getText().trim();
            String nomeProfessor = (String) cmbProfessor.getSelectedItem();
            int idProfessor = professoresMap.getOrDefault(nomeProfessor, -1);

            String erro = disciplinaController.alterarDisciplina(idDisciplinaAtual, nomeDisciplina, idProfessor);

            if (erro == null) {
                JOptionPane.showMessageDialog(this,
                        "Disciplina alterada com sucesso!",
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

        btnExcluir.addActionListener(e -> {
            if (idDisciplinaAtual == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecione uma disciplina para excluir.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir esta disciplina?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                String erro = disciplinaController.excluirDisciplina(idDisciplinaAtual);

                if (erro == null) {
                    JOptionPane.showMessageDialog(this,
                            "Disciplina excluída com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this,
                            erro,
                            "Erro ao Excluir",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnPesquisar.addActionListener(e -> {
            String nomeDisciplina = JOptionPane.showInputDialog(this,
                    "Digite o nome da disciplina para pesquisar:");

            if (nomeDisciplina != null && !nomeDisciplina.trim().isEmpty()) {
                model.Disciplina disciplinaEncontrada = disciplinaController.pesquisarDisciplina(nomeDisciplina);

                if (disciplinaEncontrada != null) {
                    txtNome_disciplina.setText(disciplinaEncontrada.getNome_disciplina());
                    cmbProfessor.setSelectedItem(getProfessorNomeById(disciplinaEncontrada.getId_professor()));
                    idDisciplinaAtual = disciplinaEncontrada.getId();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Disciplina não encontrada.",
                            "Pesquisa",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnPesquisar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal);
    }

    private void carregarProfessor() {
        List<Professor> professores = daoProfessor.listarTodos();
        cmbProfessor.removeAllItems();
        professoresMap.clear();
        cmbProfessor.addItem("Selecione um Professor");
        for (Professor professor : professores) {
            cmbProfessor.addItem(professor.getNome());
            professoresMap.put(professor.getNome(), professor.getId());
        }
    }

    private void limparCampos() {
        txtNome_disciplina.setText("");
        cmbProfessor.setSelectedIndex(0);
        idDisciplinaAtual = null;
        txtNome_disciplina.requestFocus();
    }

    private String getProfessorNomeById(int id) {
        for (Map.Entry<String, Integer> entry : professoresMap.entrySet()) {
            if (entry.getValue() == id) {
                return entry.getKey();
            }
        }
        return "Selecione um Professor";
    }
}
