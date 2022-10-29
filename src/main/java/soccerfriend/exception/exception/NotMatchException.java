package soccerfriend.exception.exception;

import lombok.Getter;
import soccerfriend.exception.ExceptionInfo;

@Getter
public class NotMatchException extends IllegalArgumentException {

    private ExceptionInfo exceptionInfo;

    public NotMatchException(ExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
