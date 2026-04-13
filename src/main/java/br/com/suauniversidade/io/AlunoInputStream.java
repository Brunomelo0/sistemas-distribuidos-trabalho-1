package br.com.suauniversidade.io;
import br.com.suauniversidade.model.Aluno;
import java.io.*;

public class AlunoInputStream extends InputStream {
    private DataInputStream in;

    public AlunoInputStream(InputStream origem) {
        this.in = new DataInputStream(origem);
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    public Aluno[] receberDados() throws IOException {
        int numObjetos = in.readInt();
        Aluno[] recebidos = new Aluno[numObjetos];

        for (int i = 0; i < numObjetos; i++) {
            int numBytes = in.readInt();
            byte[] buffer = new byte[numBytes];
            in.readFully(buffer);

            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            DataInputStream tempIn = new DataInputStream(bais);
            
            int id = tempIn.readInt();
            String nome = tempIn.readUTF();
            String matricula = tempIn.readUTF();
            
            recebidos[i] = new Aluno(id, nome, matricula);
        }
        return recebidos;
    }
}