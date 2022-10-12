package soccerfriend.exception.member;

import lombok.Getter;
import soccerfriend.exception.ExceptionCode;

import static soccerfriend.exception.ExceptionCode.PASSWORD_INCORRECT;

@Getter
public class PasswordIncorrectException extends RuntimeException{

    private ExceptionCode exceptionCode;

    public PasswordIncorrectException() {
        this.exceptionCode = PASSWORD_INCORRECT;
    }
}
