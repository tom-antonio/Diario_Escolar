package model;

public class Disciplina {

    private int id;
    private String nome_disciplina;
    private int id_professor;

    public Disciplina() {
    }

    public Disciplina(String nome_disciplina, int id_professor) {
        this.nome_disciplina = nome_disciplina;
        this.id_professor = id_professor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_disciplina() {
        return nome_disciplina;
    }

    public void setNome_disciplina(String nome_disciplina) {
        this.nome_disciplina = nome_disciplina;
    }

    public int getId_professor() {
        return id_professor;
    }

    public void setId_professor(int id_professor) {
        this.id_professor = id_professor;
    }
}