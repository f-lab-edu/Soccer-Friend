package soccerfriend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionCode {

    NO_CLUB_PERMISSION(401, "클럽 내에서 권한이 없습니다."),
    NOT_CLUB_MEMBER(401, "해당 club의 member가 아닙니다."),
    IS_CLUB_LEADER(401, "해당 club의 leader는 탈퇴할 수 없습니다."),

    ID_NOT_EXIST(404, "존재하지 않은 아이디입니다."),
    LOGIN_FORM_INCORRECT(404, "잘못된 로그인 정보입니다."),
    LOGIN_INFO_NOT_EXIST(404, "로그인 되어있지 않습니다."),
    FORM_NOT_FULL(404, "필수 입력사항이 모두 입력되지 않았습니다."),
    CLUB_ID_NOT_EXIST(404, "해당 club id가 존재하지 않습니다"),


    NOT_ENOUGH_POINT(409, "포인트가 부족합니다."),
    ALREADY_JOINED_CLUB(409, "이미 해당 클럽에 가입신청했습니다."),
    NICKNAME_DUPLICATED(409, "이미 존재하는 닉네임입니다"),
    CLUB_NAME_DUPLICATED(409, "이미 존재하는 클럽 이름입니다"),
    CLUB_MEMBER_DUPLICATED(409, "이미 존재하는 클럽 회원입니다"),
    ID_DUPLICATED(409, "이미 존재하는 아이디입니다."),
    PASSWORD_SAME(409, "새로운 비밀번호가 현재 비밀번호와 같습니다.");

    private int status;
    private String exceptionMessage;
}
