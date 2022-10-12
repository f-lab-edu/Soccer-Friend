package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

import static soccerfriend.exception.ExceptionCode.*;

@Getter
public class IdDuplicatedException extends RuntimeException {

    private ExceptionCode exceptionCode;

    public IdDuplicatedException() {
        this.exceptionCode = ID_DUPLICATED;
    }
}
