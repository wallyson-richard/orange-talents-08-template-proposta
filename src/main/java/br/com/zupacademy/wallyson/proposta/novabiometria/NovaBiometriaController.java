package br.com.zupacademy.wallyson.proposta.novabiometria;

import br.com.zupacademy.wallyson.proposta.cartao.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class NovaBiometriaController {

    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;

    public NovaBiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/api/cartoes/{id}/biometrias")
    public ResponseEntity<?> save(@PathVariable Long id,
                                  @RequestBody @Valid NovaBiometriaRequest request,
                                  UriComponentsBuilder uriComponentsBuilder) {
        var cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var biometria = request.toModel(cartao);

        biometriaRepository.save(biometria);

        var uri = uriComponentsBuilder.path("/api/biometrias/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
