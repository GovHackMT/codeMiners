package escolapp.ufmt.ic.br.escolaapp;

/**
 * Created by ''''VINI on 05/11/2016.
 */
public class Aluno {
    public int id;
    public String nome;
    public String cpf;
    public int turmaAtual;
    public String data;
    public boolean presenca = false;

    public Aluno(int id, String nome, String cpf, int turmaAtual) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.turmaAtual = turmaAtual;
    }
}