package controller;

import java.util.logging.Logger;
import model.Aluno;
import model.Professor;
import view.FormAluno;
import view.FormProfessor;

public class PessoaController {

    private static final Logger LOG = Logger.getLogger(PessoaController.class.getName());
    
    private FormAluno formAluno;
    private FormProfessor formProfessor;
    private Aluno aluno;
    private Professor professor;

    public PessoaController(){
    }

    public PessoaController(FormAluno formAluno) {
        LOG.info("Inicializando PessoaController com FormAluno");
        this.formAluno = formAluno;
        this.aluno = new Aluno();
    }

    public PessoaController(FormProfessor formProfessor) {
        LOG.info("Inicializando PessoaController com FormProfessor");
        this.formProfessor = formProfessor;
        this.professor = new Professor();
    }

    public boolean validarNome(String nome) {
        LOG.info("Validando nome: " + nome);
        if (nome == null || nome.trim().isEmpty() || !isNomeValido(nome.trim()))
            return false;
        else {
            return true;
        }
    }
    
    public boolean validarEndereco(String endereco){
        LOG.info("Validando endereço: " + endereco);
        if (endereco == null || endereco.trim().isEmpty())
            return false;
        else {
            return true;
        }
    }

    public boolean validarTelefone(String telefone){
        LOG.info("Validando telefone: " + telefone);
        if (telefone == null || telefone.trim().isEmpty())
            return false;
        else {
            return true;
        }
    }

    public boolean validarEmail(String email){
        LOG.info("Validando email: " + email);
        if (email == null || email.trim().isEmpty() || !email.contains("@"))
            return false;
        else {
            return true;
        }
    }

    private boolean isNomeValido(String nome) {
        LOG.info("Verificando formato do nome: " + nome);
        return nome.matches("^[\\p{L}]+(?:[\\s'-][\\p{L}]+)*$");
    }

}