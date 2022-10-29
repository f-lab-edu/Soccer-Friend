package soccerfriend.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private int status;
    private String exceptionMessage;

    public ExceptionResponse(ExceptionInfo exceptionInfo) {
        this.status = exceptionInfo.getStatus();
        this.exceptionMessage = exceptionInfo.getExceptionMessage();
    }
}
