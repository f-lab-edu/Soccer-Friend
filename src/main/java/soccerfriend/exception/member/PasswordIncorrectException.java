package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class PasswordIncorrectException extends RuntimeException{

    private ExceptionCode exceptionCode;

    public PasswordIncorrectException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
