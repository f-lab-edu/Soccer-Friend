package soccerfriend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    ID_DUPLICATED(409, "이미 존재하는 아이디입니다."),
    ID_NOT_EXIST(404,"존재하지 않은 아이디입니다."),
    NICKNAME_DUPLICATED(409, "이미 존재하는 닉네임입니다"),
    PASSWORD_INCORRECT(404, "잘못된 비밀번호입니다."),
    PASSWORD_SAME(409, "새로운 비밀번호가 현재 비밀번호와 같습니다.");

    private int status;
    private String exceptionMessage;
}
