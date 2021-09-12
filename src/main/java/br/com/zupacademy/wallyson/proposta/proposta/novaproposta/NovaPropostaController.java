package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.RegistroDuplicadoException;
import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("propostas")
public class NovaPropostaController {

    private final PropostaRepository propostaRepository;

    private NovaPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest request) {
        var proposta = request.toModel();

        if (proposta.unica(propostaRepository)) {
            throw new RegistroDuplicadoException("documento", "Documento já existe em nossa base de dados.");
        }

        proposta = propostaRepository.save(proposta);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
