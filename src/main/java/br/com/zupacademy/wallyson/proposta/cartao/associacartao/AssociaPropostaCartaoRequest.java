package br.com.zupacademy.wallyson.proposta.cartao.associacartao;

import br.com.zupacademy.wallyson.proposta.proposta.Proposta;

public class AssociaPropostaCartaoRequest {

    private String documento;

    private String nome;

    private String idProposta;

    @Deprecated
    public AssociaPropostaCartaoRequest() {
    }

    public AssociaPropostaCartaoRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
