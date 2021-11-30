package co.votando.accountms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class CandidatosInsuficientesAdvice {
    @ResponseBody
    @ExceptionHandler(CandidatosInsuficientesException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String EntityConflict(CandidatosInsuficientesException ex){
        return ex.getMessage();
    }
}
