package controller;

import dao.DaoNota;
import model.Nota;
import view.FormNota;

public class NotaController {

    private final DaoNota daoNota;
    private FormNota formNota;

    public NotaController() {
        this.daoNota = new DaoNota();
    }

    public NotaController(FormNota formNota) {
        this();
        this.formNota = formNota;
    }

    public String salvarNota(String nota) {

        if (nota == null || nota.isEmpty()) {
            return "Nome do nota não pode estar vazio.";
        }
        if (Double.parseDouble(nota) < 0 || Double.parseDouble(nota) > 10) {
            return "A nota deve estar entre 0 e 10.";
        }

        Nota notaObj = new Nota();
        notaObj.setNota(Double.parseDouble(nota));

        boolean salvo = daoNota.salvar(notaObj);
        if (!salvo) {
            return "Erro ao salvar nota no banco de dados.";
        }

        return null;
    }
}