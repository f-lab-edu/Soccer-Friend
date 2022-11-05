package soccerfriend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionInfo {

    NOT_STADIUM_OWNER(401, "경기장의 사업자가 아닙니다."),
    NO_CLUB_PERMISSION(401, "클럽 내에서 권한이 없습니다."),
    NOT_CLUB_MEMBER(401, "해당 클럽의 회원이 아닙니다."),
    IS_CLUB_LEADER(401, "해당 클럽의 leader는 탈퇴할 수 없습니다."),
    PAYMENT_FAIL(401, "결제에 실패했습니다."),

    POSITIONS_NOT_EXIST(404, "존재하지 않은 포지션입니다."),
    ADDRESS_NOT_EXIST(404, "존재하지 않은 주소입니다."),
    ID_NOT_EXIST(404, "존재하지 않은 아이디입니다."),
    MEMBER_NOT_EXIST(404, "해당 회원이 존재하지 않습니다."),
    STADIUM_OWNER_NOT_EXIST(404, "존재하지 않은 경기장 사업자입니다."),
    LOGIN_FORM_INCORRECT(404, "잘못된 로그인 정보입니다."),
    LOGIN_INFO_NOT_EXIST(404, "로그인 되어있지 않습니다."),
    FORM_NOT_FULL(404, "필수 입력사항이 모두 입력되지 않았습니다."),
    CLUB_ID_NOT_EXIST(404, "해당 클럽의 id가 존재하지 않습니다"),
    CLUB_MEMBER_NOT_EXIST(404, "해당 클럽회원이 존재하지 않습니다"),
    CLUB_NOT_EXIST(404, "해당 클럽이 존재하지 않습니다"),
    PAYER_TYPE_NOT_EXIST(404, "결제 대상이 아닙니다."),


    NOT_ENOUGH_POINT(409, "포인트가 부족합니다."),
    SAME_AS_HOST_CLUB(409, "경기 주최클럽과 동일한 클럽은 상대가 될 수 없습니다."),
    ALREADY_MATCH_APPROVED(409, "이미 성사된 경기입니다."),
    ALREADY_PAID_CLUB_MONTHLY_FEE(409, "이미 클럽에 월회비를 납부했습니다."),
    ALREADY_JOINED_CLUB(409, "이미 해당 클럽에 가입신청했습니다."),
    NICKNAME_DUPLICATED(409, "이미 존재하는 닉네임입니다"),
    CLUB_NAME_DUPLICATED(409, "이미 존재하는 클럽 이름입니다"),
    CLUB_MEMBER_DUPLICATED(409, "이미 존재하는 클럽 회원입니다"),
    ID_DUPLICATED(409, "이미 존재하는 아이디입니다."),
    PASSWORD_SAME(409, "새로운 비밀번호가 현재 비밀번호와 같습니다.");

    private int status;
    private String exceptionMessage;
}
