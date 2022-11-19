package soccerfriend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soccerfriend.exception.exception.*;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
