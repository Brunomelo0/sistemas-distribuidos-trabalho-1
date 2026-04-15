package br.com.suauniversidade.network;
import br.com.suauniversidade.model.dto.NotaInformativa;
import com.google.gson.Gson;
import java.net.*;
import java.util.*;

public class ServidorVotacao {
    public static volatile boolean votacaoAberta = true;
    public static final Map<String, Integer> votos = new HashMap<>();
    public static final String MULTICAST_IP = "230.0.0.0";
    public static final int MULTICAST_PORT = 4446;

    public static void main(String[] args) throws Exception {
        votos.put("Candidato A", 0);
        votos.put("Candidato B", 0);

        iniciarTemporizador(60000);

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Servidor de Votação (Ex 5) iniciado na porta 8080.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        }
    }

    private static void iniciarTemporizador(long ms) {
        new Thread(() -> {
            try {
                Thread.sleep(ms / 2);
                enviarNotaMulticast("Atenção, faltam 30 segundos para encerrar a votação!");
                Thread.sleep(ms / 2);
                
                votacaoAberta = false;
                enviarNotaMulticast("VOTAÇÃO ENCERRADA! Computando resultados...");
                calcularResultados();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void calcularResultados() {
        System.out.println("\n--- RESULTADOS FINAIS ---");
        for (Map.Entry<String, Integer> entry : votos.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votos.");
        }
    }

    public static synchronized void registrarVoto(String candidato) {
        if (votos.containsKey(candidato)) {
            votos.put(candidato, votos.get(candidato) + 1);
        }
    }

    public static void enviarNotaMulticast(String mensagem) {
        try (DatagramSocket socket = new DatagramSocket()) {
            NotaInformativa nota = new NotaInformativa("Sistema", mensagem);
            String jsonPayload = new Gson().toJson(nota);
            
            byte[] buf = jsonPayload.getBytes();
            InetAddress group = InetAddress.getByName(MULTICAST_IP);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, MULTICAST_PORT);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}