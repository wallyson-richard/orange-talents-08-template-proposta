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

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getEmititdoEm() {
        return emititdoEm;
    }

    public void setEmititdoEm(LocalDateTime emititdoEm) {
        this.emititdoEm = emititdoEm;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }
}
