package br.com.zupacademy.wallyson.proposta.proposta;

import br.com.zupacademy.wallyson.proposta.cartao.Cartao;
import br.com.zupacademy.wallyson.proposta.compartilhado.Encryptor;
import br.com.zupacademy.wallyson.proposta.proposta.novaproposta.SituacaoFinanceiraClient;
import br.com.zupacademy.wallyson.proposta.proposta.novaproposta.SituacaoFinanceiraRequest;
import br.com.zupacademy.wallyson.proposta.proposta.novaproposta.SituacaoFinanceiraResponse;
import br.com.zupacademy.wallyson.proposta.validation.annotation.CpfOrCnpj;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CpfOrCnpj
    @NotBlank
    @Convert(converter = Encryptor.class)
//    @ColumnTransformer(
//            read = "pgp_sym_decrypt(documento::bytea, 'secret.dados-sensiveis')",
//            write = "pgp_sym_encrypt(?, 'secret.dados-sensiveis')")
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @PositiveOrZero
    @NotNull
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "proposta")
    private Cartao cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@CpfOrCnpj @NotBlank String documento, @Email @NotBlank String email, @NotBlank String nome,
                    @NotBlank String endereco, @PositiveOrZero @NotNull BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void alteraStatus(StatusProposta status) {
        this.status = status;
    }

    public boolean unica(PropostaRepository propostaRepository) {
        return propostaRepository.findByDocumento(documento).isEmpty();
    }

    public void verificaSituacaoFinanceira(SituacaoFinanceiraClient situacaoFinanceiraClient) throws JsonProcessingException {
        var request = new SituacaoFinanceiraRequest(this);
        SituacaoFinanceiraResponse response;
        try {
            response = situacaoFinanceiraClient.consultar(request);
        } catch (FeignException exception) {
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.readValue(exception.contentUTF8(), SituacaoFinanceiraResponse.class);
        }
        this.alteraStatus(response.statusProposta());
    }

    public void adicionaCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
