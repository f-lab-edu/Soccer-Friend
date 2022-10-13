package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class IdDuplicatedException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public IdDuplicatedException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
