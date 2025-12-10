package controller;

import dao.DaoPeriodo;
import model.Periodo;
import view.FormPeriodo;

public class PeriodoController {

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
}