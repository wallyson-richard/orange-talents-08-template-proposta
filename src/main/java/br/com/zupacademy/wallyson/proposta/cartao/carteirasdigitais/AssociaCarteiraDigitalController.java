package br.com.zupacademy.wallyson.proposta.cartao.carteirasdigitais;

import br.com.zupacademy.wallyson.proposta.cartao.ApiCartoes;
import br.com.zupacademy.wallyson.proposta.cartao.CartaoRepository;
import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.RegistroDuplicadoException;
import br.com.zupacademy.wallyson.proposta.utils.UriUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cartoes")
public class AssociaCarteiraDigitalController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private ApiCartoes apiCartoes;

    @PostMapping("/{id}/carteiras")
    public ResponseEntity<?> associaPaypal(@PathVariable Long id, @RequestBody @Valid AssociaCarteiraDigitalRequest request) {
        var cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        if (cartao.possuiEstaCarteira(request.getCarteira())) {
            throw new RegistroDuplicadoException("cartao", "Este cartão já está associado a essa carteira digital");
        }

        apiCartoes.associaCarteira(cartao.getNumero(), request);

        var carteiraDigital = request.toModel();
        cartao.adicionaCarteiraDigital(carteiraDigital);
        cartaoRepository.save(cartao);

        carteiraDigital = cartao.buscarCarteiraPorTipo(request.getCarteira());

        var uri = UriUtils.gerarUri(carteiraDigital.getId());
        return ResponseEntity.created(uri).build();
    }
}