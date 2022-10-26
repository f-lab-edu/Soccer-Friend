package soccerfriend.exception.exception;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class NotMatchException extends IllegalArgumentException {

    private ExceptionCode exceptionCode;

    public NotMatchException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
