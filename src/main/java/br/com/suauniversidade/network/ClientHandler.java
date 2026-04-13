package br.com.suauniversidade.network;
import br.com.suauniversidade.model.dto.RequisicaoVoto;
import br.com.suauniversidade.model.dto.RespostaServidor;
import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private Gson gson = new Gson();

    public ClientHandler(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String jsonRequest = in.readLine();
            if (jsonRequest == null) return;

            RequisicaoVoto requisicao = gson.fromJson(jsonRequest, RequisicaoVoto.class);
            RespostaServidor respostaObj;

            if (!ServidorVotacao.votacaoAberta) {
                respostaObj = new RespostaServidor("ERRO", "Tempo esgotado. Votação encerrada.");
            } else if ("VOTAR".equalsIgnoreCase(requisicao.getAcao())) {
                ServidorVotacao.registrarVoto(requisicao.getCandidato());
                System.out.println("Voto registrado para: " + requisicao.getCandidato());
                respostaObj = new RespostaServidor("SUCESSO", "Voto computado com sucesso!");
            } else {
                respostaObj = new RespostaServidor("ERRO", "Ação inválida.");
            }

            out.println(gson.toJson(respostaObj));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}