package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

@Getter
public class NicknameDuplicatedException extends RuntimeException{

    private ExceptionCode exceptionCode;

    public NicknameDuplicatedException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
