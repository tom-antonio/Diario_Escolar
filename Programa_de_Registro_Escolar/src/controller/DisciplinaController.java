package controller;

import dao.DaoDisciplina;
import model.Disciplina;
import view.FormDisciplina;

public class DisciplinaController {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(DisciplinaController.class.getName());
    private final DaoDisciplina daoDisciplina;
    private FormDisciplina formDisciplina;

    public DisciplinaController() {
        this.daoDisciplina = new DaoDisciplina();
    }

    public DisciplinaController(FormDisciplina formDisciplina) {
        this();
        this.formDisciplina = formDisciplina;
    }

    public String salvarDisciplina(String nomeDisciplina, String nomeProfessor, int idProfessor) {

        LOG.info("Salvando disciplina: " + nomeDisciplina + " para o professor ID: " + idProfessor);
        if (nomeDisciplina == null || nomeDisciplina.isEmpty()) {
            return "Nome da disciplina não pode estar vazio.";
        }

        if (nomeProfessor == null || nomeProfessor.startsWith("Selecione")) {
            return "Selecione um professor.";
        }

        if (idProfessor <= 0) {
            return "Professor inválido.";
        }

        Disciplina disciplina = new Disciplina(nomeDisciplina.trim(), idProfessor);

        boolean salvo = daoDisciplina.salvar(disciplina);
        if (!salvo) {
            return "Erro ao salvar disciplina no banco de dados.";
        }

        return null; // null indica sucesso
    }

    public String alterarDisciplina(int id, String nomeDisciplina, int idProfessor) {
        LOG.info("Alterando disciplina ID " + id + " para nome: " + nomeDisciplina + " e professor ID: " + idProfessor);
        if (id <= 0) {
            return "ID inválido.";
        }

        if (nomeDisciplina == null || nomeDisciplina.trim().isEmpty()) {
            return "Nome da disciplina inválido.";
        }

        if (idProfessor <= 0) {
            return "Professor inválido.";
        }

        Disciplina disciplina = new Disciplina(nomeDisciplina.trim(), idProfessor);
        disciplina.setId(id);

        boolean alterado = daoDisciplina.alterar(disciplina);
        if (!alterado) {
            return "Erro ao alterar disciplina no banco de dados.";
        }

        return null;
    }

    public String excluirDisciplina(int id) {
        LOG.info("Excluindo disciplina ID: " + id);
        if (id <= 0) {
            return "ID inválido.";
        }

        boolean excluido = daoDisciplina.excluir(id);
        if (!excluido) {
            return "Erro ao excluir disciplina no banco de dados.";
        }

        return null;
    }

    public Disciplina pesquisarDisciplina(String nome) {
        LOG.info("Pesquisando disciplina com nome: " + nome);
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        return daoDisciplina.pesquisar(nome.trim());
    }
}
