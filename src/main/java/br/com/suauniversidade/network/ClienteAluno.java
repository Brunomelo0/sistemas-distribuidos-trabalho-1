package br.com.suauniversidade.network;
import br.com.suauniversidade.io.AlunoInputStream;
import br.com.suauniversidade.io.AlunoOutputStream;
import br.com.suauniversidade.model.Aluno;
import java.io.IOException;
import java.net.Socket;

public class ClienteAluno {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 9999)) {
            Aluno[] meusAlunos = { new Aluno(1, "Ana Maria", "MAT2026") };
            
            AlunoOutputStream out = new AlunoOutputStream(meusAlunos, 1, socket.getOutputStream());
            out.enviarDados();
            System.out.println("Cliente enviou: " + meusAlunos[0].getNome());

            AlunoInputStream in = new AlunoInputStream(socket.getInputStream());
            Aluno[] resposta = in.receberDados();
            System.out.println("Cliente recebeu resposta: " + resposta[0].getNome());
        }
    }
}