package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NovaPropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropostaRepository propostaRepository;

    private final Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        propostaRepository.deleteAll();
    }

    @Test
    void deveCadastrarNovaProposta() throws Exception {
        var request = new NovaPropostaRequest("52060293006", "pessoa@email.com", "Julio",
                "Rua 10, N° 30", new BigDecimal("3000"));

        var response = cadastrarNovaProposta(request);

        response.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("http://localhost/api/propostas/{id}"));
    }

    @Test
    void naoDeveCadastrarSeEmailForInvalido() throws Exception {
        var request = new NovaPropostaRequest("81461803020", "pessoaemail.com", "Julio",
                "Rua 10, N° 30", new BigDecimal("3000"));

        var response = cadastrarNovaProposta(request);

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("email"))
                .andExpect(jsonPath("$[0].mensagem").value("deve ser um endereço de e-mail bem formado"));
    }

    @Test
    void naoDeveCadastrarComSalarioNegativo() throws Exception {
        var request = new NovaPropostaRequest("05760445049", "pessoa@email.com", "Julio",
                "Rua 10, N° 30", new BigDecimal("-3000"));

        var response = cadastrarNovaProposta(request);

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("salario"))
                .andExpect(jsonPath("$[0].mensagem").value("deve ser maior ou igual a 0"));
    }

    @Test
    void naoDeveCadastrarComDocumentoDuplicado() throws Exception {
        var request = new NovaPropostaRequest("39724643042", "pessoa@email.com", "Julio",
                "Rua 10, N° 30", new BigDecimal("200"));

        cadastrarNovaProposta(request);

        request = new NovaPropostaRequest("39724643042", "pessoa@email.com", "Julio",
                "Rua 10, N° 30", new BigDecimal("200"));

        var response = cadastrarNovaProposta(request);

        response.andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$[0].campo").value("documento"))
                .andExpect(jsonPath("$[0].mensagem").value("Documento já existe em nossa base de dados."));
    }

    private ResultActions cadastrarNovaProposta(NovaPropostaRequest request) throws Exception {
        return mockMvc.perform(post("/api/propostas")
                .locale(new Locale("pt", "BR"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)));
    }

}
