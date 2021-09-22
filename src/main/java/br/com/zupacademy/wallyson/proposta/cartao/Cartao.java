package br.com.zupacademy.wallyson.proposta.cartao;

import br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao.StatusCartao;
import br.com.zupacademy.wallyson.proposta.proposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    private Proposta proposta;

    @NotBlank
    private String numero;

    @NotNull
    private LocalDateTime emititdoEm;

    @NotNull
    private BigDecimal limite;

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    List<StatusCartao> operacoesBloqueioCartao = new ArrayList<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(Proposta proposta, String numero, LocalDateTime emititdoEm, BigDecimal limite) {
        this.proposta = proposta;
        this.numero = numero;
        this.emititdoEm = emititdoEm;
        this.limite = limite;
    }

    public Long getId() {
        return id;
    }

    public Proposta getProposta() {
        return proposta;
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

    public List<StatusCartao> getOperacoesBloqueioCartao() {
        return operacoesBloqueioCartao;
    }

    public void adicionaBloqueio(StatusCartao status) {
        status.alteraStatusParaBloqueado();
        this.operacoesBloqueioCartao.add(status);
    }

    public boolean estaBloqueado() {
        this.getOperacoesBloqueioCartao().sort(StatusCartao::compareTo);
        var cartaoJaFoiBloqueado = this.getOperacoesBloqueioCartao().size() > 0;
        return cartaoJaFoiBloqueado && this.getOperacoesBloqueioCartao().get(0).isBloqueado();
    }
}
