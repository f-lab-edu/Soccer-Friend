package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class IdNotExistException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public IdNotExistException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
