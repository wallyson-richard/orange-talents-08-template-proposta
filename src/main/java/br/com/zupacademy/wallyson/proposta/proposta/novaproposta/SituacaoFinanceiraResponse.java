package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.StatusProposta;

public class SituacaoFinanceiraResponse {

    private String documento;
    private String nome;
    private String resultadoSolicitacao;
    private String idProposta;

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public StatusProposta statusProposta() {
        return StatusSituacaoFinanceira.valueOf(resultadoSolicitacao).getStatusProposta();
    }

}
