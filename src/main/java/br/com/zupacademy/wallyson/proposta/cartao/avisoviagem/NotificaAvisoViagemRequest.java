package br.com.zupacademy.wallyson.proposta.cartao.avisoviagem;

public class NotificaAvisoViagemRequest {

    private String destino;

    private String validoAte;

    public NotificaAvisoViagemRequest(String destino, String validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public String getValidoAte() {
        return validoAte;
    }
}
