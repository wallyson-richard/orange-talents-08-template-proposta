package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

public class NotificaBloqueioRequest {

    private String sistemaResponsavel;

    @Deprecated
    public NotificaBloqueioRequest() {
    }

    public NotificaBloqueioRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public void setSistemaResponsavel(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }
}
