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
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

    @ExceptionHandler({NotExistException.class})
    protected ResponseEntity handleIdNotExistException(NotExistException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

    @ExceptionHandler({NotMatchException.class})
    protected ResponseEntity handleNotMatchException(NotMatchException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }
}
