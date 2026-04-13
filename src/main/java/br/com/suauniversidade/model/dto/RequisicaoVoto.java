package br.com.suauniversidade.model.dto;

public class RequisicaoVoto {
    private String acao;
    private String eleitorId;
    private String candidato;

    public RequisicaoVoto(String acao, String eleitorId, String candidato) {
        this.acao = acao; this.eleitorId = eleitorId; this.candidato = candidato;
    }

    public String getAcao() { return acao; }
    public String getEleitorId() { return eleitorId; }
    public String getCandidato() { return candidato; }
}