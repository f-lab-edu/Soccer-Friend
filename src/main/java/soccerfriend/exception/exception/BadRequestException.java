package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class BadRequestException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public BadRequestException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
