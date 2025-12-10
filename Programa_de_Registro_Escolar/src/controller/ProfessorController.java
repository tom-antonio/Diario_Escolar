package controller;

import dao.DaoProfessor;
import model.Professor;
import view.FormProfessor;


public class ProfessorController {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(ProfessorController.class.getName());

    private final DaoProfessor daoProfessor;
    private final PessoaController pessoaController;
    private FormProfessor formProfessor;

    public ProfessorController() {
        this.daoProfessor = new DaoProfessor();
        this.pessoaController = new PessoaController();
    }

    public ProfessorController(FormProfessor formProfessor) {
        this();
        this.formProfessor = formProfessor;
    }

    public String salvarProfessor(String nome, String endereco, String telefone, String email, String matriculaStr) {

        LOG.info("Salvando professor: " + nome);
        if (!pessoaController.validarNome(nome)) {
            return "Nome inválido.";
        }

        if (!pessoaController.validarEndereco(endereco)) {
            return "Endereço inválido.";
        }

        if (!pessoaController.validarTelefone(telefone)) {
            return "Telefone inválido.";
        }

        if (!pessoaController.validarEmail(email)) {
            return "Email inválido.";
        }

        if (!validarMatricula(matriculaStr)) {
            return "Matrícula inválida. Deve conter exatamente 10 dígitos numéricos.";
        }

        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setEndereco(endereco);
        professor.setTelefone(telefone);
        professor.setEmail(email);
        professor.setMatricula(Long.parseLong(matriculaStr));

        boolean salvo = daoProfessor.salvar(professor);
        if (!salvo) {
            return "Erro ao salvar professor no banco de dados.";
        }

        return null; // null indica sucesso
    }

    public boolean validarMatricula(String matricula) {
        LOG.info("Validando matrícula: " + matricula);
        if (matricula == null || matricula.length() != 10) {
            return false;
        }
        try {
            Long.parseLong(matricula);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String alterarProfessor(int id, String nome, String endereco, String telefone, String email, String matriculaStr) {
        LOG.info("Alterando professor ID: " + id);
        if (id <= 0) {
            return "ID inválido.";
        }

        if (!pessoaController.validarNome(nome)) {
            return "Nome inválido.";
        }

        if (!pessoaController.validarEndereco(endereco)) {
            return "Endereço inválido.";
        }

        if (!pessoaController.validarTelefone(telefone)) {
            return "Telefone inválido.";
        }

        if (!pessoaController.validarEmail(email)) {
            return "Email inválido.";
        }

        if (!validarMatricula(matriculaStr)) {
            return "Matrícula inválida. Deve conter exatamente 10 dígitos numéricos.";
        }

        Professor professor = new Professor();
        professor.setId(id);
        professor.setNome(nome);
        professor.setEndereco(endereco);
        professor.setTelefone(telefone);
        professor.setEmail(email);
        professor.setMatricula(Long.parseLong(matriculaStr));

        boolean alterado = daoProfessor.alterar(professor);
        if (!alterado) {
            return "Erro ao alterar professor no banco de dados.";
        }

        return null;
    }

    public String excluirProfessor(int id) {
        LOG.info("Excluindo professor ID: " + id);
        if (id <= 0) {
            return "ID inválido.";
        }

        boolean excluido = daoProfessor.excluir(id);
        if (!excluido) {
            return "Erro ao excluir professor no banco de dados.";
        }

        return null;
    }

    public Professor pesquisarProfessorPorNome(String nome) {
        LOG.info("Pesquisando professor por nome: " + nome);
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        return daoProfessor.buscarPorNome(nome.trim());
    }
}