package br.com.suauniversidade.service;
import br.com.suauniversidade.model.Aluno;
import br.com.suauniversidade.model.Curso;
import br.com.suauniversidade.model.Remuneravel;

public class ServicoControleAlunosImpl implements ServicoControleAlunos {
    @Override
    public boolean matricularAluno(Aluno aluno, Curso curso) {
        curso.matricular(aluno);
        System.out.println(aluno.getNome() + " matriculado no curso " + curso.getNomeCurso());
        return true;
    }

    @Override
    public double emitirFolhaPagamento(Remuneravel aluno) {
        return aluno.calcularPagamento();
    }
}