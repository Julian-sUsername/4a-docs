package co.votando.accountms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class UrnaCerradaAdvice {
    @ResponseBody
    @ExceptionHandler(UrnaCerradaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String EntityConflict(UrnaCerradaException ex){
        return ex.getMessage();
    }
}
