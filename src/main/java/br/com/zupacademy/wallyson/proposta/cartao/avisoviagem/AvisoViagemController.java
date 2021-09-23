package br.com.zupacademy.wallyson.proposta.cartao.avisoviagem;

import br.com.zupacademy.wallyson.proposta.cartao.CartaoRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class AvisoViagemController {

    private final CartaoRepository cartaoRepository;

    public AvisoViagemController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/api/cartoes/{id}/viagem")
    @Transactional
    public void save(@PathVariable Long id, @RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                     HttpServletRequest request) {
        var cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var remoteAddress = request.getRemoteAddr();
        var userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        var avisoViagem = avisoViagemRequest.toModel(remoteAddress, userAgent);
        cartao.adicionaAviso(avisoViagem);

        cartaoRepository.save(cartao);
    }
}
