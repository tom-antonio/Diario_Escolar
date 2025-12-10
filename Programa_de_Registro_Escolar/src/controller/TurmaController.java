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

    public String alterarTurma(int id, String nomeTurma) {
        if (id <= 0) {
            return "ID inválido.";
        }

        if (nomeTurma == null || nomeTurma.isEmpty()) {
            return "Nome da turma não pode estar vazio.";
        }

        Turma turma = new Turma();
        turma.setId(id);
        turma.setNome_turma(nomeTurma);

        boolean alterado = daoTurma.alterar(turma);
        if (!alterado) {
            return "Erro ao alterar turma no banco de dados.";
        }

        return null;
    }

    public String excluirTurma(int id) {
        if (id <= 0) {
            return "ID inválido.";
        }

        boolean excluido = daoTurma.excluir(id);
        if (!excluido) {
            return "Erro ao excluir turma no banco de dados.";
        }

        return null;
    }

    public Turma pesquisarTurma(int id) {
        if (id <= 0) {
            return null;
        }

        return daoTurma.buscarPorId(id);
    }
}
