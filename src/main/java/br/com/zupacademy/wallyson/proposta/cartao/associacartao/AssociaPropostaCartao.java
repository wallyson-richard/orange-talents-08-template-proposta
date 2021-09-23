package br.com.zupacademy.wallyson.proposta.cartao.associacartao;

import br.com.zupacademy.wallyson.proposta.cartao.ApiCartoes;
import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
import br.com.zupacademy.wallyson.proposta.proposta.StatusProposta;
import br.com.zupacademy.wallyson.proposta.utils.OfuscamentoUtil;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AssociaPropostaCartao {

    private final PropostaRepository propostaRepository;
    private final ApiCartoes apiCartoes;

    private final Logger logger = LoggerFactory.getLogger(AssociaPropostaCartao.class);

    public AssociaPropostaCartao(PropostaRepository propostaRepository, ApiCartoes apiCartoes) {
        this.propostaRepository = propostaRepository;
        this.apiCartoes = apiCartoes;
    }

    @Scheduled(fixedDelayString = "${associar.proposta.cartao.time.schedule}")
    public void associa() {
        var propostas = propostaRepository.findByStatusAndCartaoIsNull(StatusProposta.ELEGIVEL);

        logger.info(String.format("Existem %d propostas elegíveis que ainda não possuem cartão de crédito associado",
                propostas.size()));

        propostas.forEach(proposta -> {
            var request = new AssociaPropostaCartaoRequest(proposta);
            try {
                var response = apiCartoes.associaCartao(request);
                var cartao = response.toModel(proposta);
                proposta.adicionaCartao(cartao);
                propostaRepository.save(proposta);
                logger.info("Cartão {} foi associado a proposta de id {}",
                        OfuscamentoUtil.cartao(cartao.getNumero()),
                        proposta.getId());
            } catch (FeignException exception) {
                logger.warn("Erro ao associar a proposta de ID {} a um cartão. Uma nova tentativa será feita em breve",
                        proposta.getId());
            }
        });
    }
}