package br.com.zupacademy.wallyson.proposta.cartao.avisoviagem;

import br.com.zupacademy.wallyson.proposta.cartao.ApiCartoes;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class NotificaAvisoViagem {

    private final ApiCartoes apiCartoes;

    public NotificaAvisoViagem(ApiCartoes apiCartoes) {
        this.apiCartoes = apiCartoes;
    }

    public NotificaAvisoViagemResponse processar(String numeroCartao, AvisoViagem avisoViagem) {
        var destino = avisoViagem.getDestino();
        var validoAte = avisoViagem.getTerminoViagem().format(DateTimeFormatter.ofPattern("2021-09-23"));
        var request = new NotificaAvisoViagemRequest(destino, validoAte);
        return apiCartoes.notificarViagem(numeroCartao, request);
    }
}
