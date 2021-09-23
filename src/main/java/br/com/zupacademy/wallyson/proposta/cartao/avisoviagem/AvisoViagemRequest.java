package br.com.zupacademy.wallyson.proposta.cartao.avisoviagem;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @FutureOrPresent
    private LocalDate terminoViagem;

    public AvisoViagemRequest(String destino, LocalDate terminoViagem) {
        this.destino = destino;
        this.terminoViagem = terminoViagem;
    }

    public AvisoViagem toModel(String ip, String userAgent) {
        return new AvisoViagem(destino, terminoViagem, ip, userAgent);
    }
}
