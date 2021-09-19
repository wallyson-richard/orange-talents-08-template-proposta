package br.com.zupacademy.wallyson.proposta.proposta;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Base64;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private byte[] fingerprint;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(String fingerprint, Cartao cartao) {
        this.fingerprint = Base64.getEncoder().encode(fingerprint.getBytes());
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
