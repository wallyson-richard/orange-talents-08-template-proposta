package br.com.zupacademy.wallyson.proposta.proposta.associacartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "associa-cartao", url = "${feign.client.associa-cartao.url}")
public interface AssociaPropostaCartaoClient {

    @PostMapping
    AssociaPropostaCartaoResponse associaCartao(AssociaPropostaCartaoRequest request);
}
