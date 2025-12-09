package controller;

import model.Aluno;
import view.FormAluno;

public class AlunoController {

    private FormAluno formAluno;
    private Aluno aluno;

    public AlunoController(){
    }

    public AlunoController(FormAluno formAluno) {
        this.formAluno = formAluno;
        this.aluno = new Aluno();
    }

    public boolean validarMatricula(String matricula) {
        if (matricula.length() != 10) {
            return false;
        }
        try {
            Long.parseLong(matricula);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
