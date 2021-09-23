package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

import br.com.zupacademy.wallyson.proposta.cartao.Cartao;

public interface EventoBloqueioCartao {

    NotificaBloqueioResponse processar(Cartao cartao, NotificaBloqueioRequest request);
}
