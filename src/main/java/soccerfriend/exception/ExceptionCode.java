package soccerfriend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    ID_NOT_EXIST(404, "존재하지 않은 아이디입니다."),
    LOGIN_FORM_INCORRECT(404, "잘못된 로그인 정보입니다."),
    LOGIN_INFO_NOT_EXIST(404, "로그인 되어있지 않습니다."),

    NICKNAME_DUPLICATED(409, "이미 존재하는 닉네임입니다"),
    ID_DUPLICATED(409, "이미 존재하는 아이디입니다."),
    PASSWORD_SAME(409, "새로운 비밀번호가 현재 비밀번호와 같습니다.");

    private int status;
    private String exceptionMessage;
}
