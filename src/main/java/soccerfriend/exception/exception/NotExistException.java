package soccerfriend.exception.exception;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class NotExistException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public NotExistException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
