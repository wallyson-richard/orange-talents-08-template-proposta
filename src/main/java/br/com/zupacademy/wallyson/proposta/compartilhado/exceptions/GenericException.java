package br.com.zupacademy.wallyson.proposta.compartilhado.exceptions;

public class GenericException extends RuntimeException {

    private String campo;
    private String mensagem;

    public GenericException(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
