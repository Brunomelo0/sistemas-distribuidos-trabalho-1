package br.com.suauniversidade.model;

public class Aluno {
    protected int id;
    protected String nome;
    protected String matricula;

    public Aluno(int id, String nome, String matricula) {
        this.id = id;
        this.nome = nome;
        this.matricula = matricula;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public void setNome(String nome) { this.nome = nome; }
}