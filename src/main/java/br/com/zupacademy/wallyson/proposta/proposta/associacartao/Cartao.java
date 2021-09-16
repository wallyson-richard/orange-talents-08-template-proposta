package br.com.zupacademy.wallyson.proposta.proposta.associacartao;

import br.com.zupacademy.wallyson.proposta.proposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Deprecated
    public Cartao() {
    }

    public Cartao(Proposta proposta, String numero, LocalDateTime emititdoEm, BigDecimal limite) {
        this.proposta = proposta;
        this.numero = numero;
        this.emititdoEm = emititdoEm;
        this.limite = limite;
    }
}
