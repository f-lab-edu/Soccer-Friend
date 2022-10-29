package soccerfriend.exception.exception;

import lombok.Getter;
import soccerfriend.exception.ExceptionInfo;

@Getter
public class NotExistException extends RuntimeException {

    private ExceptionInfo exceptionInfo;

    public NotExistException(ExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
