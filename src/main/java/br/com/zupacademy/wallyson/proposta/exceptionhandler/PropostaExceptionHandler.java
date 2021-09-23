package br.com.zupacademy.wallyson.proposta.exceptionhandler;

import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.GenericException;
import br.com.zupacademy.wallyson.proposta.compartilhado.response.ErrorMessageResponse;
import br.com.zupacademy.wallyson.proposta.compartilhado.response.ErrorResponse;
import feign.FeignException;
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

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(FeignException.class)
    public ErrorMessageResponse feignException(FeignException ex) {
        return new ErrorMessageResponse("Ocorreu um erro durante a operação. Tente novamente mais tarde");
    }

}
