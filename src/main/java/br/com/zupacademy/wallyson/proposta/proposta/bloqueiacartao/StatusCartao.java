package br.com.zupacademy.wallyson.proposta.proposta.bloqueiacartao;

import br.com.zupacademy.wallyson.proposta.proposta.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class StatusCartao implements Comparable<StatusCartao> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String enderecoIp;

    @NotBlank
    private String userAgent;

    @Enumerated(EnumType.STRING)
    private SituacaoCartao situacao;

    @ManyToOne
    private Cartao cartao;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public StatusCartao() {
    }

    public StatusCartao(String enderecoIp, String userAgent, Cartao cartao) {
        this.enderecoIp = enderecoIp;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public void alteraStatusParaBloqueado() {
        this.situacao = SituacaoCartao.BLOQUEADO;
    }

    public boolean isBloqueado() {
        return this.situacao == SituacaoCartao.BLOQUEADO;
    }

    @Override
    public int compareTo(StatusCartao operacao) {
        return this.id > operacao.id ? -1 : 1;
    }
}
