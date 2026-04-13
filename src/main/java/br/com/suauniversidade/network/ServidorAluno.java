package br.com.suauniversidade.network;
import br.com.suauniversidade.io.AlunoInputStream;
import br.com.suauniversidade.io.AlunoOutputStream;
import br.com.suauniversidade.model.Aluno;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAluno {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Servidor TCP (Ex 4) aguardando conexão na porta 9999...");
            
            try (Socket socket = serverSocket.accept()) {
                System.out.println("Cliente conectado!");

                AlunoInputStream in = new AlunoInputStream(socket.getInputStream());
                Aluno[] alunosRecebidos = in.receberDados();
                System.out.println("Servidor recebeu: " + alunosRecebidos[0].getNome());

                alunosRecebidos[0].setNome(alunosRecebidos[0].getNome() + " (Confirmado pelo Servidor)");
                
                AlunoOutputStream out = new AlunoOutputStream(alunosRecebidos, 1, socket.getOutputStream());
                out.enviarDados();
            }
        }
    }
}