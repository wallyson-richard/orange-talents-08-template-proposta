package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
import br.com.zupacademy.wallyson.proposta.compartilhado.utils.UriUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private SituacaoFinanceiraClient situacaoFinanceiraClient;

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
