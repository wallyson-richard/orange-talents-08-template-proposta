package br.com.zupacademy.wallyson.proposta.exceptionhandler;

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
}
