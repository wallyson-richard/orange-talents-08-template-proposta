package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.StatusProposta;

public class SituacaoFinanceiraResponse {

    private String documento;
    private String nome;
    private StatusSituacaoFinanceira resultadoSolicitacao;
    private String idProposta;

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public StatusSituacaoFinanceira getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public StatusProposta statusProposta() {
        return resultadoSolicitacao.getStatusProposta();
    }

}
