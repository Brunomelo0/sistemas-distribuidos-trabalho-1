package br.com.suauniversidade.model;
import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nomeCurso;
    private List<Aluno> alunos = new ArrayList<>();

    public Curso(String nomeCurso) { this.nomeCurso = nomeCurso; }
    public String getNomeCurso() { return nomeCurso; }
    public List<Aluno> getAlunos() { return alunos; }
    public void matricular(Aluno aluno) { this.alunos.add(aluno); }
}