package controller;

import model.Aluno;
import model.Professor;
import view.FormAluno;
import view.FormProfessor;

public class PessoaController {

    private FormAluno formAluno;
    private FormProfessor formProfessor;
    private Aluno aluno;
    private Professor professor;

    public PessoaController(){
    }

    public PessoaController(FormAluno formAluno) {
        this.formAluno = formAluno;
        this.aluno = new Aluno();
    }

    public PessoaController(FormProfessor formProfessor) {
        this.formProfessor = formProfessor;
        this.professor = new Professor();
    }

    public boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty() || !isNomeValido(nome.trim()))
            return false;
        else {
            return true;
        }
    }
    
    public boolean validarEndereco(String endereco){
        if (endereco == null || endereco.trim().isEmpty())
            return false;
        else {
            return true;
        }
    }

    public boolean validarTelefone(String telefone){
        if (telefone == null || telefone.trim().isEmpty())
            return false;
        else {
            return true;
        }
    }

    public boolean validarEmail(String email){
        if (email == null || email.trim().isEmpty() || !email.contains("@"))
            return false;
        else {
            return true;
        }
    }

    private boolean isNomeValido(String nome) {
        return nome.matches("^[\\p{L}]+(?:[\\s'-][\\p{L}]+)*$");
    }

}