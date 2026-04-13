package br.com.suauniversidade.service;
import br.com.suauniversidade.model.Aluno;
import br.com.suauniversidade.model.Curso;
import br.com.suauniversidade.model.Remuneravel;

public interface ServicoControleAlunos {
    boolean matricularAluno(Aluno aluno, Curso curso);
    double emitirFolhaPagamento(Remuneravel aluno);
}