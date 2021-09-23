package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

import br.com.zupacademy.wallyson.proposta.cartao.Cartao;
import br.com.zupacademy.wallyson.proposta.utils.OfuscamentoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificaBloqueioCartaoContas implements EventoBloqueioCartao {

    private final NotificaBloqueioCartaoContasClient client;

    private final Logger logger = LoggerFactory.getLogger(NotificaBloqueioCartaoContas.class);

    public NotificaBloqueioCartaoContas(NotificaBloqueioCartaoContasClient client) {
        this.client = client;
    }

    @Override
    public NotificaBloqueioResponse processar(Cartao cartao, NotificaBloqueioRequest request) {
        var response = client.notificar(cartao.getNumero(), request);
        logger.info("O sistema contas foi notificado, e retornou que o cartão {} está com o status {}",
                OfuscamentoUtil.cartao(cartao.getNumero()),
                response.getResultado());
        return response;
    }
}
