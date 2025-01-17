package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

import br.com.zupacademy.wallyson.proposta.cartao.Cartao;
import br.com.zupacademy.wallyson.proposta.cartao.ApiCartoes;
import br.com.zupacademy.wallyson.proposta.compartilhado.utils.OfuscamentoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificaBloqueioCartao implements EventoBloqueioCartao {

    @Autowired
    private ApiCartoes client;

    private final Logger logger = LoggerFactory.getLogger(NotificaBloqueioCartao.class);

    @Override
    public NotificaBloqueioResponse processar(Cartao cartao, NotificaBloqueioRequest request) {
        var response = client.notificarBloqueio(cartao.getNumero(), request);
        logger.info("O sistema contas foi notificado, e retornou que o cartão {} está com o status {}",
                OfuscamentoUtil.cartao(cartao.getNumero()),
                response.getResultado());
        return response;
    }
}
