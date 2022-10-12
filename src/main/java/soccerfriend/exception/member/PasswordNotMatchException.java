package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class PasswordNotMatchException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public PasswordNotMatchException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
