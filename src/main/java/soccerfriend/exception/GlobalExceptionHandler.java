package soccerfriend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soccerfriend.exception.exception.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DuplicatedException.class})
    protected ResponseEntity handleIdDuplicatedException(DuplicatedException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionInfo()),
                HttpStatus.valueOf(ex.getExceptionInfo().getStatus()));
    }

    @ExceptionHandler({NotExistException.class})
    protected ResponseEntity handleIdNotExistException(NotExistException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionInfo()),
                HttpStatus.valueOf(ex.getExceptionInfo().getStatus()));
    }

    @ExceptionHandler({NotMatchException.class})
    protected ResponseEntity handleNotMatchException(NotMatchException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionInfo()),
                HttpStatus.valueOf(ex.getExceptionInfo().getStatus()));
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionInfo()),
                HttpStatus.valueOf(ex.getExceptionInfo().getStatus()));
    }

    @ExceptionHandler({NoPermissionException.class})
    protected ResponseEntity handleNoPermissionException(NoPermissionException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionInfo()),
                HttpStatus.valueOf(ex.getExceptionInfo().getStatus()));
    }

}
