package soccerfriend.exception.exception;

import lombok.Getter;
import soccerfriend.exception.ExceptionInfo;

@Getter
public class DuplicatedException extends RuntimeException {

    private ExceptionInfo exceptionInfo;

    public DuplicatedException(ExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
