package br.com.zupacademy.wallyson.proposta.proposta.novabiometria;

import br.com.zupacademy.wallyson.proposta.proposta.Biometria;
import br.com.zupacademy.wallyson.proposta.proposta.Cartao;

import javax.validation.constraints.NotBlank;

public class NovaBiometriaRequest {

    @NotBlank
    private String fingerprint;

    @Deprecated
    public NovaBiometriaRequest() {
    }

    public NovaBiometriaRequest(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(fingerprint, cartao);
     }
}
