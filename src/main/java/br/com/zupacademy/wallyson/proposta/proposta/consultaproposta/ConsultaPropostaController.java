package br.com.zupacademy.wallyson.proposta.proposta.consultaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/propostas")
public class ConsultaPropostaController {

    private final PropostaRepository propostaRepository;

    public ConsultaPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return propostaRepository.findById(id)
                .map(PropostaResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
