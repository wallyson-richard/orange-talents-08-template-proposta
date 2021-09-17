package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
import br.com.zupacademy.wallyson.proposta.utils.UriUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/propostas")
public class NovaPropostaController {

    private final PropostaRepository propostaRepository;
    private final SituacaoFinanceiraClient situacaoFinanceiraClient;


    public NovaPropostaController(PropostaRepository propostaRepository, SituacaoFinanceiraClient situacaoFinanceiraClient) {
        this.propostaRepository = propostaRepository;
        this.situacaoFinanceiraClient = situacaoFinanceiraClient;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest request) throws JsonProcessingException {
        var proposta = request.toModel(propostaRepository);

        propostaRepository.save(proposta);

        proposta.verificaSituacaoFinanceira(situacaoFinanceiraClient);

        URI uri = UriUtils.gerarUri(proposta.getId());
        return ResponseEntity.created(uri).build();
    }
}
