package soccerfriend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soccerfriend.exception.member.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IdDuplicatedException.class})
    protected ResponseEntity handleIdDuplicatedException(IdDuplicatedException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

    @ExceptionHandler({IdNotExistException.class})
    protected ResponseEntity handleIdNotExistException(IdNotExistException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

    @ExceptionHandler({NicknameDuplicatedException.class})
    protected ResponseEntity handleNicknameDuplicatedException(NicknameDuplicatedException ex) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@");
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

    @ExceptionHandler({PasswordIncorrectException.class})
    protected ResponseEntity handlePasswordIncorrectException(PasswordIncorrectException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }

    @ExceptionHandler({PasswordSameException.class})
    protected ResponseEntity handlePasswordSameException(PasswordSameException ex) {
        return new ResponseEntity(new ExceptionResponse(ex.getExceptionCode()),
                HttpStatus.valueOf(ex.getExceptionCode().getStatus()));
    }
}
