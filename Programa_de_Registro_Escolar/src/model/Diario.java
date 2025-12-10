package model;
import java.util.ArrayList;
import java.util.List;

public class Diario {

    private int id;
    private boolean status;
    private List<Aluno> aluno;
    private List<Disciplina> disciplina;
    private List<Periodo> periodo;
    private List<Turma> turma;
    private final List<Nota> notas;
    public Diario() {
        this.notas = new ArrayList<>();
    }

    public Diario(int id, boolean status, List<Aluno> aluno, List<Disciplina> disciplina, List<Periodo> periodo,
            List<Turma> turma) {
        this.id = id;
        this.status = status;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.periodo = periodo;
        this.turma = turma;
        this.notas = new ArrayList<>();  // Diário cria suas próprias notas
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Aluno> getAluno() {
        return aluno;
    }

    public void setAluno(List<Aluno> aluno) {
        this.aluno = aluno;
    }

    public List<Disciplina> getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(List<Disciplina> disciplina) {
        this.disciplina = disciplina;
    }

    public List<Periodo> getPeriodo() {
        return periodo;
    }

    public void setPeriodo(List<Periodo> periodo) {
        this.periodo = periodo;
    }

    public List<Turma> getTurma() {
        return turma;
    }

    public void setTurma(List<Turma> turma) {
        this.turma = turma;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        if (notas != null) {
            this.notas.clear();
            this.notas.addAll(notas);
        }
    }
}
