package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class NotMatchException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public NotMatchException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
