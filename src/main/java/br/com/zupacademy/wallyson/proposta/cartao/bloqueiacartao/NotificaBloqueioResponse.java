package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

public class NotificaBloqueioResponse {

    private StatusNotificacaoBloqueioCartao resultado;

    @Deprecated
    public NotificaBloqueioResponse() {
    }

    public StatusNotificacaoBloqueioCartao getResultado() {
        return resultado;
    }

    public void setResultado(StatusNotificacaoBloqueioCartao resultado) {
        this.resultado = resultado;
    }
}
