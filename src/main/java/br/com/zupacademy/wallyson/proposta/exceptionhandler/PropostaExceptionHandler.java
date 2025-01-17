package br.com.zupacademy.wallyson.proposta.exceptionhandler;

import br.com.zupacademy.wallyson.proposta.compartilhado.exceptions.GenericException;
import br.com.zupacademy.wallyson.proposta.compartilhado.response.ErrorMessageResponse;
import br.com.zupacademy.wallyson.proposta.compartilhado.response.ErrorResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PropostaExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors()
                .stream()
                .map(ErrorResponse::new)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorMessageResponse httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ErrorMessageResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(GenericException.class)
    public List<ErrorResponse> registroDuplicadoException(GenericException ex) {
        return List.of(new ErrorResponse(ex.getCampo(), ex.getMensagem()));
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(FeignException.class)
    public ErrorMessageResponse feignException(FeignException ex) {
        logger.error("Ocorreu uma falha ao tentar se comunicar com um serviço externo");
        logger.error(ex.getMessage());
        return new ErrorMessageResponse("Ocorreu um erro durante a operação. Tente novamente mais tarde");
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> responseStatusException(ResponseStatusException ex) {
        if (ex.getReason() != null ){
            return ResponseEntity.status(ex.getStatus())
                    .body(new ErrorMessageResponse(ex.getReason()));
        }
        return ResponseEntity.status(ex.getStatus()).build();
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(Exception.class)
    public ErrorMessageResponse exception(Exception ex) {
        ex.printStackTrace();
        return new ErrorMessageResponse(ex.getMessage());
    }

}
