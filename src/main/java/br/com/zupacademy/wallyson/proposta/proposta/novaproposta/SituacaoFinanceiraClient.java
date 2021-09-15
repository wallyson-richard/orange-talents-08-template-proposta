package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "situacao-financeira", url = "${feign.client.situacao-financeira.url}")
public interface SituacaoFinanceiraClient {

    @PostMapping
    SituacaoFinanceiraResponse consultar(SituacaoFinanceiraRequest situacaoFinanceiraRequest);
}
