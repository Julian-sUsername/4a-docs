package co.votando.accountms.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody

public class CandidatoNoEncontradoAdvice {
    @ResponseBody
    @ExceptionHandler(CandidatoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String EntityNotFoundAdvice(CandidatoNoEncontradoException ex){
        return ex.getMessage();
    }
}