package br.com.zupacademy.wallyson.proposta.cartao.associacartao;

import br.com.zupacademy.wallyson.proposta.cartao.ApiCartoes;
import br.com.zupacademy.wallyson.proposta.compartilhado.utils.OfuscamentoUtil;
import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
import br.com.zupacademy.wallyson.proposta.proposta.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AssociaPropostaCartao {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private ApiCartoes apiCartoes;

    private final Logger logger = LoggerFactory.getLogger(AssociaPropostaCartao.class);

    @Scheduled(fixedDelayString = "${associar.proposta.cartao.time.schedule}")
    public void associa() {
        while (true) {
            var propostas = propostaRepository.findTop100ByStatusAndCartaoIsNullOrderByIdAsc(StatusProposta.ELEGIVEL);

            if (propostas.isEmpty()) {
                break;
            }

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
}