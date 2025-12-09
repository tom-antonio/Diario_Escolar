package model;


public class Professor extends Pessoa {

    private long matricula;

    public Professor() {
        super();
    }

    public Professor(int id, String nome, String endereco, String telefone, String email, long matricula) {
        super(id, nome, endereco, telefone, email);
        this.matricula = matricula;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }
}
