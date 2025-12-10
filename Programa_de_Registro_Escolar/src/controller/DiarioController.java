package controller;

import dao.DaoDiario;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JToggleButton;
import model.Diario;
import model.Nota;
import view.FormDiario;

public class DiarioController {
    
    private final DaoDiario daoDiario;
    private FormDiario formDiario;

    public DiarioController() {
        this.daoDiario = new DaoDiario();
    }

    public DiarioController(FormDiario formDiario) {
        this();
        this.formDiario = formDiario;
    }
    
    public double calcularMedia(List<Double> notas) {
        if (notas.isEmpty()) return 0;
        
        double soma = 0;
        for (Double nota : notas) {
            soma += nota;
        }
        
        return soma / notas.size();
    }
    
    public void atualizarExibicao(List<Double> notas, DefaultListModel<String> modeloLista, JToggleButton togStatus) {
        // Limpa lista
        modeloLista.clear();
        
        // Adiciona notas na JList
        for (int i = 0; i < notas.size(); i++) {
            modeloLista.addElement(String.format("%.2f", notas.get(i)));
        }
        
        // Atualiza status
        if (notas.isEmpty()) {
            togStatus.setSelected(true);
            atualizarTextoStatus(togStatus);
        } else {
            double media = calcularMedia(notas);
            togStatus.setSelected(media > 6);
            atualizarTextoStatus(togStatus);
        }
    }
    
    public void atualizarTextoStatus(JToggleButton togStatus) {
        if (togStatus.isSelected()) {
            togStatus.setText("Aprovado");
        } else {
            togStatus.setText("Reprovado");
        }
    }

    public String salvarDiario(int idAluno, int idDisciplina, int idPeriodo, int idTurma, List<Double> notasValores, boolean status) {
        if (idAluno <= 0) {
            return "Aluno inválido.";
        }

        if (idDisciplina <= 0) {
            return "Disciplina inválida.";
        }

        if (idPeriodo <= 0) {
            return "Período inválido.";
        }

        if (idTurma <= 0) {
            return "Turma inválida.";
        }

        if (notasValores == null || notasValores.isEmpty()) {
            return "Adicione pelo menos uma nota.";
        }

        // Criar objeto Diario
        Diario diario = new Diario();
        diario.setStatus(status);

        // Converter List<Double> para List<Nota>
        List<Nota> notas = new java.util.ArrayList<>();
        for (Double valor : notasValores) {
            Nota nota = new Nota();
            nota.setNota(valor);
            notas.add(nota);
        }
        diario.setNotas(notas);

        boolean salvo = daoDiario.salvar(diario, idAluno, idDisciplina, idPeriodo, idTurma);
        if (!salvo) {
            return "Erro ao salvar diário no banco de dados.";
        }

        return null;
    }

    public String alterarDiario(int idDiario, int idAluno, int idDisciplina, int idPeriodo, int idTurma, List<Double> notasValores, boolean status) {
        if (idDiario <= 0) {
            return "ID do diário inválido.";
        }

        if (idAluno <= 0) {
            return "Aluno inválido.";
        }

        if (idDisciplina <= 0) {
            return "Disciplina inválida.";
        }

        if (idPeriodo <= 0) {
            return "Período inválido.";
        }

        if (idTurma <= 0) {
            return "Turma inválida.";
        }

        if (notasValores == null || notasValores.isEmpty()) {
            return "Adicione pelo menos uma nota.";
        }

        // Criar objeto Diario
        Diario diario = new Diario();
        diario.setId(idDiario);
        diario.setStatus(status);

        // Converter List<Double> para List<Nota>
        List<Nota> notas = new java.util.ArrayList<>();
        for (Double valor : notasValores) {
            Nota nota = new Nota();
            nota.setNota(valor);
            notas.add(nota);
        }
        diario.setNotas(notas);

        boolean alterado = daoDiario.alterar(diario, idAluno, idDisciplina, idPeriodo, idTurma);
        if (!alterado) {
            return "Erro ao alterar diário no banco de dados.";
        }

        return null;
    }

    public String excluirDiario(int idDiario) {
        if (idDiario <= 0) {
            return "ID do diário inválido.";
        }

        boolean excluido = daoDiario.excluir(idDiario);
        if (!excluido) {
            return "Erro ao excluir diário no banco de dados.";
        }

        return null;
    }

    public Diario pesquisarDiario(String nomeAluno, String nomeDisciplina) {
        if (nomeAluno == null || nomeAluno.trim().isEmpty()) {
            return null;
        }

        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            return null;
        }

        return daoDiario.pesquisarAluno(nomeAluno.trim(), nomeDisciplina.trim());
    }
}
