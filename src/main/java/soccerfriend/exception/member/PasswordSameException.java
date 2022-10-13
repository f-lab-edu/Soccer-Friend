package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class PasswordSameException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public PasswordSameException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
