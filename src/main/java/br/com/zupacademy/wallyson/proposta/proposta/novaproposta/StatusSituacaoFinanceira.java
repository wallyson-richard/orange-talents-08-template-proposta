package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.StatusProposta;

public enum StatusSituacaoFinanceira {
    COM_RESTRICAO(StatusProposta.NAO_ELEGIVEL),
    SEM_RESTRICAO(StatusProposta.ELEGIVEL);

    private final StatusProposta statusProposta;

    StatusSituacaoFinanceira(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }
}
