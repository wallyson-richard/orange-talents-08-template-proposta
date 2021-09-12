package br.com.zupacademy.wallyson.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.exceptionhandler.ErrorResponse;
import br.com.zupacademy.wallyson.proposta.proposta.novaproposta.NovaPropostaRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NovaPropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Gson gson = new Gson();

    @Test
    void deveCadastrarNovaProposta() throws Exception {
        var request = new NovaPropostaRequest("12193697051", "pessoa@email.com", "Julio",
                "Rua 10, N° 30", new BigDecimal("3000"));

        var response = cadastrarNovaProposta(request);

        response.andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(redirectedUrlPattern("http://localhost/propostas/{id}"));
    }

    @Test
    void naoDeveCadastrarSeEmaiForInvalido() throws Exception {
        var request = new NovaPropostaRequest("12193697051", "pessoaemail.com", "Julio",
                "Rua 10, N° 30", new BigDecimal("3000"));

        var response = cadastrarNovaProposta(request);

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("email"))
                .andExpect(jsonPath("$[0].mensagem").value("deve ser um endereço de e-mail bem formado"));
    }

    @Test
    void naoDeveCadastrarComSalarioNegativo() throws Exception {
        var request = new NovaPropostaRequest("12193697051", "pessoa@email.com", "Julio",
                "Rua 10, N° 30", new BigDecimal("-3000"));

        var response = cadastrarNovaProposta(request);

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("salario"))
                .andExpect(jsonPath("$[0].mensagem").value("deve ser maior ou igual a 0"));
    }

    private ResultActions cadastrarNovaProposta(Object request) throws Exception {
        return mockMvc.perform(post("/propostas")
                .locale(new Locale("pt", "BR"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)));
    }

}
