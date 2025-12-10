package controller;

import dao.DaoPeriodo;
import model.Periodo;
import view.FormPeriodo;

public class PeriodoController {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(PeriodoController.class.getName());
    private final DaoPeriodo daoPeriodo;
    private FormPeriodo formPeriodo;

    public PeriodoController() {
        this.daoPeriodo = new DaoPeriodo();
    }

    public PeriodoController(FormPeriodo formPeriodo) {
        this();
        this.formPeriodo = formPeriodo;
    }

    public String salvarPeriodo(String nomePeriodo) {

        LOG.info("Salvando período: " + nomePeriodo);
        if (nomePeriodo == null || nomePeriodo.isEmpty()) {
            return "Nome do periodo não pode estar vazio.";
        }

        Periodo periodo = new Periodo();
        periodo.setNome_periodo(nomePeriodo);

        boolean salvo = daoPeriodo.salvar(periodo);
        if (!salvo) {
            return "Erro ao salvar período no banco de dados.";
        }

        return null;
    }

    public String alterarPeriodo(int id, String nomePeriodo) {
        LOG.info("Alterando período ID " + id + " para nome: " + nomePeriodo);
        if (id <= 0) {
            return "ID inválido.";
        }

        if (nomePeriodo == null || nomePeriodo.isEmpty()) {
            return "Nome do período não pode estar vazio.";
        }

        Periodo periodo = new Periodo();
        periodo.setId(id);
        periodo.setNome_periodo(nomePeriodo);

        boolean alterado = daoPeriodo.alterar(periodo);
        if (!alterado) {
            return "Erro ao alterar período no banco de dados.";
        }

        return null;
    }

    public String excluirPeriodo(int id) {
        LOG.info("Excluindo período ID: " + id);
        if (id <= 0) {
            return "ID inválido.";
        }

        boolean excluido = daoPeriodo.excluir(id);
        if (!excluido) {
            return "Erro ao excluir período no banco de dados.";
        }

        return null;
    }

    public Periodo pesquisarPeriodo(int id) {
        LOG.info("Pesquisando período com ID: " + id);
        if (id <= 0) {
            return null;
        }

        return daoPeriodo.buscarPorId(id);
    }
}
