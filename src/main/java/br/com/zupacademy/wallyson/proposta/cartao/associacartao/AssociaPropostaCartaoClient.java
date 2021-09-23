package br.com.zupacademy.wallyson.proposta.cartao.associacartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "associa-cartao", url = "${feign.client.contas-cartoes.url}")
public interface AssociaPropostaCartaoClient {

    @PostMapping
    AssociaPropostaCartaoResponse associaCartao(AssociaPropostaCartaoRequest request);
}
