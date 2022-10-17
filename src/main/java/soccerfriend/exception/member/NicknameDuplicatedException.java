package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class NicknameDuplicatedException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public NicknameDuplicatedException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
