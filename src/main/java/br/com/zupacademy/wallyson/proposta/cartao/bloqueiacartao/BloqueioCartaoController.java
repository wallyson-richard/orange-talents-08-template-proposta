package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

import br.com.zupacademy.wallyson.proposta.cartao.CartaoRepository;
import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.RegraDeNegocioException;
import br.com.zupacademy.wallyson.proposta.compartilhado.utils.OfuscamentoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.transaction.Transactional;

@RestController
public class BloqueioCartaoController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private NotificaBloqueioCartao notificadorBloqueio;

    private final Logger logger = LoggerFactory.getLogger(BloqueioCartaoController.class);

    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/api/cartoes/{id}/bloqueio")
    public void bloqueiaCartao(@PathVariable Long id, HttpServletRequest request, @AuthenticationPrincipal Jwt jwt) {
        var cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        var documentoUsuarioLogado = jwt.getClaims().get("documento");
        var remoteAddress = request.getRemoteAddr();
        var userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        var cartaoNaoPertenceAoUsuarioLogado = !cartao.getProposta().getDocumento().equals(documentoUsuarioLogado);

        if (cartaoNaoPertenceAoUsuarioLogado) {
            logger.warn("Cartão {} não pertence ao usuário de documento {}",
                    OfuscamentoUtil.cartao(cartao.getNumero()),
                    OfuscamentoUtil.documento(documentoUsuarioLogado.toString()));
            throw new RegraDeNegocioException("cartao", "Cartão informado não pertence a esse usuário.");
        }

        if (cartao.estaBloqueado()) {
            logger.warn("O cartão {} já está bloqueado", OfuscamentoUtil.cartao(cartao.getNumero()));
            throw new RegraDeNegocioException("cartao", "Este cartão já está bloqueado.");
        }

        if (remoteAddress.isEmpty() || userAgent.isEmpty()) {
            logger.warn("A requisição está incompleta, não tem IP ou User-Agent.");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var requestNotificar = new NotificaBloqueioRequest("propostas");
        var responseNotificacao = notificadorBloqueio.processar(cartao, requestNotificar);

        if (responseNotificacao.getResultado() == StatusNotificacaoBloqueioCartao.BLOQUEADO) {
            var operacaoBloqueioCartao = new BloqueioCartao(remoteAddress, userAgent);
            cartao.adicionaBloqueio(operacaoBloqueioCartao);
            cartaoRepository.save(cartao);
            logger.info("Cartão {} foi bloqueado com sucesso.", OfuscamentoUtil.cartao(cartao.getNumero()));
        } else {
            logger.warn("Cartão {} foi bloqueado sistema legado retornou resultado diferente de BLOQUEADO",
                    OfuscamentoUtil.cartao(cartao.getNumero()));
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }

    }
}
