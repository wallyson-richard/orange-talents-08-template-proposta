package br.com.zupacademy.wallyson.proposta.consultaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ConsultaPropostaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PropostaRepository propostaRepository;

    @Autowired
    CartaoRepository cartaoRepository;

    Gson gson = new Gson();

    @BeforeEach
    void setup() {
        cartaoRepository.deleteAll();
        propostaRepository.deleteAll();
    }

    @Test
    void deveRetornarOkCasoPropostaExista() throws Exception {
        var proposta = new Proposta("76973410006", "email@email.com", "Pessoa", "Endereco", BigDecimal.valueOf(500));
        proposta.alteraStatus(StatusProposta.ELEGIVEL);
        propostaRepository.save(proposta);

        var cartao = new Cartao(proposta, "1234-1234-1234-1234", LocalDateTime.now(), BigDecimal.valueOf(500));
        proposta.adicionaCartao(cartao);
        cartaoRepository.save(cartao);

        var response = consultarProposta(proposta.getId());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(proposta.getId()))
                .andExpect(jsonPath("$.documento").value(proposta.getDocumento()))
                .andExpect(jsonPath("$.email").value(proposta.getEmail()))
                .andExpect(jsonPath("$.nome").value(proposta.getNome()))
                .andExpect(jsonPath("$.endereco").value(proposta.getEndereco()))
                .andExpect(jsonPath("$.salario").value(proposta.getSalario().doubleValue()))
                .andExpect(jsonPath("$.status").value(proposta.getStatus().toString()))
                .andExpect(jsonPath("$.cartao.id").value(cartao.getId()))
                .andExpect(jsonPath("$.cartao.numero").value(cartao.getNumero()))
                .andExpect(jsonPath("$.cartao.emititdoEm").exists())
                .andExpect(jsonPath("$.cartao.limite").value(cartao.getLimite().doubleValue()));
    }

    @Test
    void deveRetornarBadRequestCasoPropostaNaoExista() throws Exception {
        var response = consultarProposta(1L);

        response.andExpect(status().isBadRequest());
    }

    private ResultActions consultarProposta(Long idProposta) throws Exception {
        var url = String.format("/api/propostas/%d", idProposta);

        return mockMvc.perform(get(url)
                .locale(new Locale("pt", "BR"))
                .contentType(MediaType.APPLICATION_JSON));
    }
}
