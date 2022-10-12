package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

import static soccerfriend.exception.ExceptionCode.*;

@Getter
public class NicknameDuplicatedException extends RuntimeException{

    private ExceptionCode exceptionCode;

    public NicknameDuplicatedException() {
        this.exceptionCode = NICKNAME_DUPLICATED;
    }
}
