package br.com.zupacademy.wallyson.proposta.proposta.novaproposta;

import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.RegistroDuplicadoException;
import br.com.zupacademy.wallyson.proposta.proposta.Proposta;
import br.com.zupacademy.wallyson.proposta.proposta.PropostaRepository;
import br.com.zupacademy.wallyson.proposta.utils.OfuscamentoUtil;
import br.com.zupacademy.wallyson.proposta.validation.annotation.CpfOrCnpj;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @CpfOrCnpj
    @NotBlank
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @PositiveOrZero
    private BigDecimal salario;

    public NovaPropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel(PropostaRepository propostaRepository) {
        var proposta = new Proposta(documento, email, nome, endereco, salario);

        if (proposta.unica(propostaRepository)) {
            var logger = LoggerFactory.getLogger(NovaPropostaRequest.class);
            logger.warn("O documento {} já existe em nossa base de dados,", OfuscamentoUtil.documento(proposta.getDocumento()));
            throw new RegistroDuplicadoException("documento", "Documento já existe em nossa base de dados.");
        }

        return proposta;
    }
}
