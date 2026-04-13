package br.com.suauniversidade.model.dto;

public class RespostaServidor {
    private String status;
    private String mensagem;

    public RespostaServidor(String status, String mensagem) {
        this.status = status; this.mensagem = mensagem;
    }

    public String getStatus() { return status; }
    public String getMensagem() { return mensagem; }
}