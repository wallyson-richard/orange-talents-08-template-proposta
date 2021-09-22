package br.com.zupacademy.wallyson.proposta.proposta.consultaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.Proposta;
import br.com.zupacademy.wallyson.proposta.proposta.StatusProposta;

public class PropostaResponse {

    private Long id;

    private StatusProposta status;

    public PropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.status = proposta.getStatus();
    }

    public Long getId() {
        return id;
    }

    public StatusProposta getStatus() {
        return status;
    }
}
