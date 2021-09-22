package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

import br.com.zupacademy.wallyson.proposta.cartao.CartaoRepository;
import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.RegraDeNegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BloqueioCartaoController {

    private final CartaoRepository cartaoRepository;

    public BloqueioCartaoController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/cartoes/{id}/bloqueio")
    public void bloqueiaCartao(@PathVariable Long id, HttpServletRequest request, @AuthenticationPrincipal Jwt jwt) {
        var cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var documentoUsuarioLogado = jwt.getClaims().get("documento");
        var remoteAddress = request.getRemoteAddr();
        var userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        var cartaoPertenceAoUsuarioLogado = cartao.getProposta().getDocumento().equals(documentoUsuarioLogado);

        if (!cartaoPertenceAoUsuarioLogado) {
            throw new RegraDeNegocioException("cartao", "Cartão informado não pertence a esse usuário.");
        }

        if (cartao.estaBloqueado()) {
            throw new RegraDeNegocioException("cartao", "Este cartão já está bloqueado.");
        }

        if (remoteAddress.isEmpty() || userAgent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var operacaoBloqueioCartao = new StatusCartao(remoteAddress, userAgent, cartao);
        cartao.adicionaBloqueio(operacaoBloqueioCartao);
        cartaoRepository.save(cartao);
    }
}
