package br.com.zupacademy.wallyson.proposta.cartao.avisoviagem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String destino;

    @NotNull
    private LocalDate terminoViagem;

    private String ip;

    private String userAgent;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(String destino, LocalDate terminoViagem, String ip, String userAgent) {
        this.destino = destino;
        this.terminoViagem = terminoViagem;
        this.ip = ip;
        this.userAgent = userAgent;
    }
}
