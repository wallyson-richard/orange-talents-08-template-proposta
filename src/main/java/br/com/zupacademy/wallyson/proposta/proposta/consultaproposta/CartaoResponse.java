package br.com.zupacademy.wallyson.proposta.proposta.consultaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.Cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoResponse {

    private Long id;

    private String numero;

    private LocalDateTime emititdoEm;

    private BigDecimal limite;

    public CartaoResponse(Cartao cartao) {
        this.id = cartao.getId();
        this.numero = cartao.getNumero();
        this.emititdoEm = cartao.getEmititdoEm();
        this.limite = cartao.getLimite();
    }

    public Long getId() {
        return id;
    }


    public String getNumero() {
        return numero;
    }


    public LocalDateTime getEmititdoEm() {
        return emititdoEm;
    }


    public BigDecimal getLimite() {
        return limite;
    }
}
