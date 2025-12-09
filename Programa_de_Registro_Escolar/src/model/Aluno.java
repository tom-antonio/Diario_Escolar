package model;

public class Aluno extends Pessoa {

    private long matricula;
    private String nome_pai;
    private String nome_mae;

    public Aluno() {
        super();
    }

    public Aluno(int id, String nome, String endereco, String telefone, String email, long matricula, String nome_pai, String nome_mae) {
        super(id, nome, endereco, telefone, email);
        this.matricula = matricula;
        this.nome_pai = nome_pai;
        this.nome_mae = nome_mae;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getNome_pai() {
        return nome_pai;
    }

    public void setNome_pai(String nome_pai) {
        this.nome_pai = nome_pai;
    }

    public String getNome_mae() {
        return nome_mae;
    }

    public void setNome_mae(String nome_mae) {
        this.nome_mae = nome_mae;
    }
}
