package controller;

import dao.DaoTurma;
import model.Turma;
import view.FormTurma;

public class TurmaController {

    private final DaoTurma daoTurma;
    private FormTurma formTurma;

    public TurmaController() {
        this.daoTurma = new DaoTurma();
    }

    public TurmaController(FormTurma formTurma) {
        this();
        this.formTurma = formTurma;
    }

    public String salvarTurma(String nomeTurma) {

        if (nomeTurma == null || nomeTurma.isEmpty()) {
            return "Nome do turma não pode estar vazio.";
        }

        Turma turma = new Turma();
        turma.setNome_turma(nomeTurma);

        boolean salvo = daoTurma.salvar(turma);
        if (!salvo) {
            return "Erro ao salvar turma no banco de dados.";
        }

        return null;
    }
}