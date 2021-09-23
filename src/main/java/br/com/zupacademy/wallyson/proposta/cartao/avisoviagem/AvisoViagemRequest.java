package br.com.zupacademy.wallyson.proposta.cartao.avisoviagem;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate terminoViagem;

    public AvisoViagemRequest(String destino, LocalDate terminoViagem) {
        this.destino = destino;
        this.terminoViagem = terminoViagem;
    }

    public AvisoViagem toModel(String ip, String userAgent) {
        return new AvisoViagem(destino, terminoViagem, ip, userAgent);
    }
}
