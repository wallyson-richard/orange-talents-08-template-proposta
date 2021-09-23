package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

import br.com.zupacademy.wallyson.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class BloqueioCartao implements Comparable<BloqueioCartao> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String enderecoIp;

    @NotBlank
    private String userAgent;

    @Enumerated(EnumType.STRING)
    private StatusNotificacaoBloqueioCartao situacao;

    @ManyToOne
    private Cartao cartao;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public BloqueioCartao() {
    }

    public BloqueioCartao(String enderecoIp, String userAgent, Cartao cartao) {
        this.enderecoIp = enderecoIp;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public void alteraStatusParaBloqueado() {
        this.situacao = StatusNotificacaoBloqueioCartao.BLOQUEADO;
    }

    public boolean isBloqueado() {
        return this.situacao == StatusNotificacaoBloqueioCartao.BLOQUEADO;
    }

    @Override
    public int compareTo(BloqueioCartao operacao) {
        return this.id > operacao.id ? -1 : 1;
    }
}