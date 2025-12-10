package controller;

import dao.DaoAluno;
import model.Aluno;
import view.FormAluno;

public class AlunoController {

    private final DaoAluno daoAluno;
    private final PessoaController pessoaController;
    private FormAluno formAluno;

    public AlunoController() {
        this.daoAluno = new DaoAluno();
        this.pessoaController = new PessoaController();
    }

    public AlunoController(FormAluno formAluno) {
        this();
        this.formAluno = formAluno;
    }

    public String salvarAluno(String nome, String endereco, String telefone, String email, String matriculaStr, String nomePai, String nomeMae) {

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

        if (nomePai != null && !nomePai.isBlank() && !pessoaController.validarNome(nomePai)) {
            return "Nome do pai inválido.";
        }

        if (nomeMae != null && !nomeMae.isBlank() && !pessoaController.validarNome(nomeMae)) {
            return "Nome da mãe inválido.";
        }

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setEndereco(endereco);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
        aluno.setMatricula(Long.parseLong(matriculaStr));
        aluno.setNome_pai(nomePai);
        aluno.setNome_mae(nomeMae);

        boolean salvo = daoAluno.salvar(aluno);
        if (!salvo) {
            return "Erro ao salvar aluno no banco de dados.";
        }

        return null; // null indica sucesso
    }

    public boolean validarMatricula(String matricula) {
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

    public String alterarAluno(int id, String nome, String endereco, String telefone, String email, String matriculaStr, String nomePai, String nomeMae) {
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

        if (nomePai != null && !nomePai.isBlank() && !pessoaController.validarNome(nomePai)) {
            return "Nome do pai inválido.";
        }

        if (nomeMae != null && !nomeMae.isBlank() && !pessoaController.validarNome(nomeMae)) {
            return "Nome da mãe inválido.";
        }

        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setNome(nome);
        aluno.setEndereco(endereco);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
        aluno.setMatricula(Long.parseLong(matriculaStr));
        aluno.setNome_pai(nomePai);
        aluno.setNome_mae(nomeMae);

        boolean alterado = daoAluno.alterar(aluno);
        if (!alterado) {
            return "Erro ao alterar aluno no banco de dados.";
        }

        return null;
    }

    public String excluirAluno(int id) {
        if (id <= 0) {
            return "ID inválido.";
        }

        boolean excluido = daoAluno.excluir(id);
        if (!excluido) {
            return "Erro ao excluir aluno no banco de dados.";
        }

        return null;
    }

    public Aluno pesquisarAluno(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        return daoAluno.buscarPorNome(nome.trim());
    }
}
