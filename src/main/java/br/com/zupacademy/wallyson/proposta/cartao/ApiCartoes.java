package br.com.zupacademy.wallyson.proposta.cartao;

import br.com.zupacademy.wallyson.proposta.cartao.associacartao.AssociaPropostaCartaoRequest;
import br.com.zupacademy.wallyson.proposta.cartao.associacartao.AssociaPropostaCartaoResponse;
import br.com.zupacademy.wallyson.proposta.cartao.avisoviagem.NotificaAvisoViagemRequest;
import br.com.zupacademy.wallyson.proposta.cartao.avisoviagem.NotificaAvisoViagemResponse;
import br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao.NotificaBloqueioRequest;
import br.com.zupacademy.wallyson.proposta.cartao.bloqueiacartao.NotificaBloqueioResponse;
import br.com.zupacademy.wallyson.proposta.cartao.carteirasdigitais.AssociaCarteiraDigitalRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "api-cartoes", url = "${feign.client.cartoes.url}")
public interface ApiCartoes {

    @PostMapping(path = "/{numeroCartao}/bloqueios")
    NotificaBloqueioResponse notificarBloqueio(@PathVariable String numeroCartao, NotificaBloqueioRequest request);

    @PostMapping("/{numeroCartao}/avisos")
    NotificaAvisoViagemResponse notificarViagem(@PathVariable String numeroCartao, NotificaAvisoViagemRequest request);

    @PostMapping
    AssociaPropostaCartaoResponse associaCartao(AssociaPropostaCartaoRequest request);

    @PostMapping("/{numeroCartao}/carteiras")
    void associaCarteira(@PathVariable String numeroCartao, AssociaCarteiraDigitalRequest request);
}
