package br.com.zupacademy.wallyson.proposta.compartilhado.exceptions;

public class RegraDeNegocioException extends GenericException {

    public RegraDeNegocioException(String campo, String mensagem) {
        super(campo, mensagem);
    }
}
