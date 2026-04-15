package br.com.suauniversidade.network;
import br.com.suauniversidade.model.dto.NotaInformativa;
import br.com.suauniversidade.model.dto.RequisicaoVoto;
import br.com.suauniversidade.model.dto.RespostaServidor;
import com.google.gson.Gson;
import java.io.*;
import java.net.*;

public class ClienteEleitor {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();

        // 1. Thread de Multicast
        new Thread(() -> {
            try (MulticastSocket socket = new MulticastSocket(4446)) {
                InetAddress group = InetAddress.getByName("230.0.0.0");
                socket.joinGroup(group);

                while (true) {
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    
                    String recebido = new String(packet.getData(), 0, packet.getLength());
                    NotaInformativa nota = new Gson().fromJson(recebido, NotaInformativa.class);
                    System.out.println("\n[Aviso do " + nota.getAutor() + "]: " + nota.getAviso());
                }
            } catch (Exception e) {
                System.out.println("Multicast encerrado.");
            }
        }).start();

        // 2. Thread Principal Unicast TCP
        Thread.sleep(1000);
        System.out.println("Conectando ao servidor para votar...");

        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            RequisicaoVoto meuVoto = new RequisicaoVoto("VOTAR", "User-777", "Candidato A");
            String jsonEnviado = gson.toJson(meuVoto);
            out.println(jsonEnviado);

            String jsonResposta = in.readLine();
            RespostaServidor resposta = gson.fromJson(jsonResposta, RespostaServidor.class);
            
            System.out.println("\nStatus: " + resposta.getStatus());
            System.out.println("Mensagem: " + resposta.getMensagem());
            System.out.println("Aguardando fim da eleição via multicast...\n");
        }
    }
}