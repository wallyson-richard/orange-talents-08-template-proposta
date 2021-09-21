package br.com.zupacademy.wallyson.proposta.compartilhado.exceptions;

public class RegistroDuplicadoException extends GenericException {

    public RegistroDuplicadoException(String campo, String mensagem) {
        super(campo, mensagem);
    }
}