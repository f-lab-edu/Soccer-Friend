package soccerfriend.exception.exception;

import lombok.Getter;
import soccerfriend.exception.ExceptionInfo;

@Getter
public class NoPermissionException extends RuntimeException {

    private ExceptionInfo exceptionInfo;

    public NoPermissionException(ExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
