package br.com.zupacademy.wallyson.proposta.novabiometria;

import br.com.zupacademy.wallyson.proposta.proposta.*;
import br.com.zupacademy.wallyson.proposta.proposta.novabiometria.NovaBiometriaRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NovaBiometriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BiometriaRepository biometriaRepository;

    private final Gson gson = new Gson();

    @BeforeEach
    void setup() {
        biometriaRepository.deleteAll();
        cartaoRepository.deleteAll();
        propostaRepository.deleteAll();
    }

    @Test
    void deveCadastrarComRequisicaoCorreta() throws Exception {
        var proposta = new Proposta("76973410006", "email@email.com", "Pessoa", "Endereco",
                BigDecimal.TEN);
        propostaRepository.save(proposta);

        var cartao = new Cartao(proposta, "1234-1234-1234-1234", LocalDateTime.now(), BigDecimal.TEN);
        cartaoRepository.save(cartao);

        var request = new NovaBiometriaRequest("Base64");

        var response = cadastrarNovaBiometria(request, cartao.getId());

        response.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("http://localhost/api/biometrias/{id}"));
    }

    @Test
    void naoDeveCadastrarBiometriaCasoCartaoNaoExista() throws Exception {
        var request = new NovaBiometriaRequest("Base64");

        var response = cadastrarNovaBiometria(request, 1L);

        response.andExpect(status().isNotFound());
    }

    @Test
    void naoDeveCadastrarBiometriaCasoFingerprintVazio() throws Exception {
        var request = new NovaBiometriaRequest("");

        var response = cadastrarNovaBiometria(request, 1L);

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("fingerprint"))
                .andExpect(jsonPath("$[0].mensagem").value("não deve estar em branco"));
    }

    private ResultActions cadastrarNovaBiometria(Object request, Long idCartao) throws Exception {
        var url =  String.format("/api/cartoes/%d/biometrias", idCartao);

        return mockMvc.perform(post(url)
                .locale(new Locale("pt", "BR"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)));
    }
}
