package soccerfriend.exception.exception;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class NoPermissionException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public NoPermissionException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
