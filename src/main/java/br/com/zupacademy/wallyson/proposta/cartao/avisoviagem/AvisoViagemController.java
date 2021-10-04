package br.com.zupacademy.wallyson.proposta.cartao.avisoviagem;

import br.com.zupacademy.wallyson.proposta.cartao.CartaoRepository;
import br.com.zupacademy.wallyson.proposta.compartilhado.utils.OfuscamentoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private NotificaAvisoViagem notificaAvisoViagem;

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    @PostMapping("/api/cartoes/{id}/viagem")
    @Transactional
    public void save(@PathVariable Long id, @RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                     HttpServletRequest request) {
        var cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var remoteAddress = request.getRemoteAddr();
        var userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        var avisoViagem = avisoViagemRequest.toModel(remoteAddress, userAgent);

        var responseNotificao = notificaAvisoViagem.processar(cartao.getNumero(), avisoViagem);

        if (responseNotificao.getResultado() == StatusAvisoViagem.CRIADO) {
            logger.info("A notificação da viagem do cartão {} foi realizada com sucesso",
                    OfuscamentoUtil.cartao(cartao.getNumero()));
            cartao.adicionaAviso(avisoViagem);
            cartaoRepository.save(cartao);
        }
    }
}
