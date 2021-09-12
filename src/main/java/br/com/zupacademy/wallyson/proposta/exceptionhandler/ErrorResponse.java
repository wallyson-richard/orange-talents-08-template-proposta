package br.com.zupacademy.wallyson.proposta.exceptionhandler;

import org.springframework.validation.FieldError;

public class ErrorResponse {

    private String campo;
    private String mensagem;

    public ErrorResponse(FieldError fieldError) {
        this.campo = fieldError.getField();
        this.mensagem = fieldError.getDefaultMessage();
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
