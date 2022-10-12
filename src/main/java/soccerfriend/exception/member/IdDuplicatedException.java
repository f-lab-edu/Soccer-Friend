package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class IdDuplicatedException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public IdDuplicatedException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
