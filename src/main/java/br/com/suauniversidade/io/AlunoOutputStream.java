package br.com.suauniversidade.io;
import br.com.suauniversidade.model.Aluno;
import java.io.*;

public class AlunoOutputStream extends OutputStream {
    private Aluno[] alunos;
    private int numObjetos;
    private DataOutputStream out;

    public AlunoOutputStream(Aluno[] alunos, int numObjetos, OutputStream destino) {
        this.alunos = alunos;
        this.numObjetos = numObjetos;
        this.out = new DataOutputStream(destino);
    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }

    public void enviarDados() throws IOException {
        out.writeInt(numObjetos);
        for (int i = 0; i < numObjetos; i++) {
            Aluno a = alunos[i];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream tempOut = new DataOutputStream(baos);
            tempOut.writeInt(a.getId());
            tempOut.writeUTF(a.getNome());
            tempOut.writeUTF(a.getMatricula());
            tempOut.flush();
            
            byte[] dadosAtributos = baos.toByteArray();
            out.writeInt(dadosAtributos.length); // Tamanho em bytes
            out.write(dadosAtributos);           // Os bytes propriamente ditos
        }
        out.flush();
    }
}