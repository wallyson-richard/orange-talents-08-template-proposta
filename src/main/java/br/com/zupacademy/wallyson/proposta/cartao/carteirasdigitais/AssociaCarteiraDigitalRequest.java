package br.com.zupacademy.wallyson.proposta.cartao.carteirasdigitais;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AssociaCarteiraDigitalRequest {

    @Email
    @NotBlank
    private String email;

    @NotNull
    private TipoCarteira carteira;

    public AssociaCarteiraDigitalRequest(String email, TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }

    public Carteira toModel() {
        return new Carteira(email, carteira);
    }
}
