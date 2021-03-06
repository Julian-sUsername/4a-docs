package co.votando.accountms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class UrnaVotadaAdvice {
    @ResponseBody
    @ExceptionHandler(UrnaVotadaException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String EntityConflict(UrnaVotadaException ex){
        return ex.getMessage();
    }
}
