package controller;

import dao.DaoDisciplina;
import model.Disciplina;
import view.FormDisciplina;

public class DisciplinaController {

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
}