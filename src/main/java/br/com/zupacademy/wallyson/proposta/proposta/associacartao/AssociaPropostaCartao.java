package br.com.zupacademy.wallyson.proposta.proposta.associacartao;

import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
import br.com.zupacademy.wallyson.proposta.proposta.StatusProposta;
import feign.FeignException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AssociaPropostaCartao {

    private final PropostaRepository propostaRepository;
    private final AssociaPropostaCartaoClient associaPropostaCartaoClient;

    public AssociaPropostaCartao(PropostaRepository propostaRepository, AssociaPropostaCartaoClient associaPropostaCartaoClient) {
        this.propostaRepository = propostaRepository;
        this.associaPropostaCartaoClient = associaPropostaCartaoClient;
    }

    @Scheduled(fixedDelayString = "${associar.proposta.cartao.time.schedule}")
    public void associa() {
        var propostas = propostaRepository.findByStatusAndCartaoIsNull(StatusProposta.ELEGIVEL);

        propostas.forEach(proposta -> {
            var request = new AssociaPropostaCartaoRequest(proposta);
            try {
                var response = associaPropostaCartaoClient.associaCartao(request);
                var cartao = response.toModel(proposta);
                proposta.adicionaCartao(cartao);
                propostaRepository.save(proposta);
            } catch (FeignException exception) {
                exception.printStackTrace();
            }
        });
    }
}