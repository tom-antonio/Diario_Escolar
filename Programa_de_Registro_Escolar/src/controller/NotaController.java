package controller;

import dao.DaoNota;
import model.Nota;

public class NotaController {

    private final DaoNota daoNota;
    
    public NotaController() {
        this.daoNota = new DaoNota();
    }

    public NotaController(DaoNota daoNota) {
        this.daoNota = daoNota;
    }

    public boolean salvarNotasPorDiario(int idDiario, java.util.List<Nota> notas) {
        if (idDiario <= 0) {
            return false;
        }

        if (notas == null || notas.isEmpty()) {
            return true; // Sem notas para salvar
        }

        return daoNota.salvarNotasPorDiario(idDiario, notas);
    }

    public boolean excluirNotasPorDiario(int idDiario) {
        if (idDiario <= 0) {
            return false;
        }

        return daoNota.excluirNotasPorDiario(idDiario);
    }
}
