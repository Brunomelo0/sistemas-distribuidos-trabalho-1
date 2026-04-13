package br.com.suauniversidade.model.dto;

public class NotaInformativa {
    private String autor;
    private String aviso;

    public NotaInformativa(String autor, String aviso) {
        this.autor = autor; this.aviso = aviso;
    }

    public String getAutor() { return autor; }
    public String getAviso() { return aviso; }
}