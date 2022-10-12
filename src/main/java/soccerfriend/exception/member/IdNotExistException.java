package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

import static soccerfriend.exception.ExceptionCode.ID_NOT_EXIST;

@Getter
public class IdNotExistException extends RuntimeException{

    private ExceptionCode exceptionCode;

    public IdNotExistException() {
        this.exceptionCode = ID_NOT_EXIST;
    }
}
