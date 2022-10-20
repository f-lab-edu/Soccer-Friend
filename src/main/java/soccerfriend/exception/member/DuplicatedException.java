package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class DuplicatedException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public DuplicatedException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
