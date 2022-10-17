package soccerfriend.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private int status;
    private String exceptionMessage;

    public ExceptionResponse(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getStatus();
        this.exceptionMessage = exceptionCode.getExceptionMessage();
    }
}
