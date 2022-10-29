package soccerfriend.exception.exception;

import lombok.Getter;
import soccerfriend.exception.ExceptionInfo;

@Getter
public class BadRequestException extends RuntimeException {

    private ExceptionInfo exceptionInfo;

    public BadRequestException(ExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
