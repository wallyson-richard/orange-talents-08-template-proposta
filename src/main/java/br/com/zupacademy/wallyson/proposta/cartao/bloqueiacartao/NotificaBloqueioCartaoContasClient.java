package br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notifica-bloqueio-cartao", url = "${feign.client.contas-cartoes.url}")
public interface NotificaBloqueioCartaoContasClient {

    @PostMapping(path = "/{numeroCartao}/bloqueios")
    NotificaBloqueioResponse notificar(@PathVariable String numeroCartao, NotificaBloqueioRequest request);
}
