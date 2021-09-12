package br.com.zupacademy.wallyson.proposta.validation.annotation;

import br.com.zupacademy.wallyson.proposta.proposta.Proposta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;

public class CpfOrCnpjTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        var factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void naoValidarCpfInvalido() {
        var proposta = new Proposta("31171192051", "pessoa@email.com", "Pessoa",
                "Avenida Brasil", new BigDecimal("2000"));

        var violations = validator.validate(proposta);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void naoValidarCnpjInvalido() {
        var proposta = new Proposta("34312535000111", "pessoa@email.com", "Pessoa",
                "Avenida Brasil", new BigDecimal("2000"));

        var violations = validator.validate(proposta);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void validarCpfValido() {
        var proposta = new Proposta("31171192053", "pessoa@email.com", "Pessoa",
                "Avenida Brasil", new BigDecimal("2000"));

        var violations = validator.validate(proposta);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void validarCnpjValido() {
        var proposta = new Proposta("34312535000110", "pessoa@email.com", "Pessoa",
                "Avenida Brasil", new BigDecimal("2000"));

        var violations = validator.validate(proposta);

        Assertions.assertEquals(0, violations.size());
    }
}
