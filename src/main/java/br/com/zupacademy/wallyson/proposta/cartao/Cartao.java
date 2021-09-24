package br.com.zupacademy.wallyson.proposta.cartao;

import br.com.zupacademy.wallyson.proposta.cartao.avisoviagem.AvisoViagem;
import br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao.BloqueioCartao;
import br.com.zupacademy.wallyson.proposta.cartao.carteirasdigitais.Carteira;
import br.com.zupacademy.wallyson.proposta.cartao.carteirasdigitais.TipoCarteira;
import br.com.zupacademy.wallyson.proposta.proposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JoinColumn(name = "cartao_id")
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<BloqueioCartao> operacoesBloqueioCartao = new ArrayList<>();

    @JoinColumn(name = "cartao_id")
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<AvisoViagem> avisosViagens = new ArrayList<>();

    @JoinColumn(name = "cartao_id")
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Carteira> carteiras = new HashSet<>();

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

    public List<BloqueioCartao> getOperacoesBloqueioCartao() {
        return operacoesBloqueioCartao;
    }

    public void adicionaBloqueio(BloqueioCartao status) {
        status.alteraStatusParaBloqueado();
        this.operacoesBloqueioCartao.add(status);
    }

    public void adicionaAviso(AvisoViagem avisoViagem) {
        this.avisosViagens.add(avisoViagem);
    }

    public boolean estaBloqueado() {
        this.getOperacoesBloqueioCartao().sort(BloqueioCartao::compareTo);
        var cartaoJaFoiBloqueado = this.getOperacoesBloqueioCartao().size() > 0;
        return cartaoJaFoiBloqueado && this.getOperacoesBloqueioCartao().get(0).isBloqueado();
    }

    public void adicionaCarteiraDigital(Carteira carteira) {
        carteiras.add(carteira);
    }

    public boolean possuiEstaCarteira(TipoCarteira tipoCarteira) {
        return carteiras.stream().anyMatch(carteira -> carteira.getTipoCarteira() == tipoCarteira);
    }

    public Carteira buscarCarteiraPorTipo(TipoCarteira tipoCarteira) {
        return this.carteiras
                .stream()
                .filter(carteira1 -> carteira1.getTipoCarteira() == tipoCarteira)
                .findFirst()
                .get();
    }
}
