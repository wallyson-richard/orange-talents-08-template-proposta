package br.com.zupacademy.wallyson.proposta.exceptionhandler;

import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.ErrorResponse;
import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PropostaExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors()
                .stream()
                .map(ErrorResponse::new)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(GenericException.class)
    public List<ErrorResponse> registroDuplicadoException(GenericException ex) {
        return List.of(new ErrorResponse(ex.getCampo(), ex.getMensagem()));
    }
}
