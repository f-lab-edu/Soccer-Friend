package soccerfriend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionInfo {

    RECENTLY_CREATE_POST(401, "게시물 작성 후 1분 동안 게시물 작성이 제한됩니다."),
    CHANGE_PASSWORD_REQUIRED(401, "비밀번호를 변경해주세요"),
    NOT_LOGIN(401, "로그인되어있지 않습니다."),
    NO_ORDER_PERMISSION(401, "해당 주문에 관한 권한이 없습니다."),
    NOT_CLUB_OF_SOCCER_MATCH(401, "경기에 참여하는 club이 아닙니다."),
    NOT_STADIUM_OWNER(401, "경기장의 사업자가 아닙니다."),
    NO_CLUB_PERMISSION(401, "클럽 내에서 권한이 없습니다."),
    NO_SUBMIT_MATCH_PERMISSION(401, "클럽결과 제출은 주최클럽의 운영진만 가능합니다."),
    NOT_CLUB_MEMBER(401, "해당 클럽의 회원이 아닙니다."),
    IS_CLUB_LEADER(401, "해당 클럽의 leader는 탈퇴할 수 없습니다."),
    PAYMENT_FAIL(401, "결제에 실패했습니다."),
    NO_COMMENT_PERMISSION(401, "해당 댓글을 수정혹은 삭제할 권한이 없습니다."),

    TOO_MUCH_FILES(404, "파일의 개수가 3개가 넘습니다."),
    FILE_NOT_EXIST(404, "파일이 존재하지 않습니다."),
    IMAGE_NOT_EXIST(404, "이미지가 존재하지 않습니다"),
    COMMENT_NOT_EXIST(404, "댓글이 존재하지 않습니다."),
    POST_PAGE_NOT_EXIST(404, "해당 게시판에 존재하지 않는 페이지입니다."),
    POST_NOT_EXIST(404, "게시물이 존재하지 않습니다."),
    CODE_NOT_EXIST(404, "존재하지 않는 코드입니다."),
    TOSS_PAYMENT_FAIL(404, "토스페이먼트 결제승인과정에서 오류가 발생했습니다."),
    ORDER_INFO_NOT_EXIST(404, "주문 정보가 존재하지 않습니다."),
    CLUB_HAS_NO_RECORD(404, "해당 클럽은 기록을 저장하고 있지 않습니다."),
    NOT_PROPER_GOAL(404, "골에 대한 정보가 정확하지 않습니다. 세트와 시간을 다시 확인해주세요."),
    GOAL_NOT_EXIST(404, "존재하지 않은 골입니다."),
    SOCCER_MATCH_MEMBER_NOT_EXIST(404, "존재하지 않은 경기참가선수입니다."),
    SOCCER_MATCH_RECRUITMENT_NOT_EXIST(404, "존재하지 않은 경기모집공고입니다."),
    CLUB_NOT_EXIST_ON_SOCCER_MATCH(404, "해당 경기에 참여하지 않는 클럽입니다."),
    SOCCER_MATCH_NOT_EXIST(404, "존재하지 않은 경기입니다."),
    POSITIONS_NOT_EXIST(404, "존재하지 않은 포지션입니다."),
    ADDRESS_NOT_EXIST(404, "존재하지 않은 주소입니다."),
    EMAIL_NOT_EXIST(404, "존재하지 않은 이메일입니다."),
    BULLETIN_NOT_EXIST(404, "존재하지 않은 게시판입니다."),
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

    SAME_NAME_FOR_UPDATE(409, "이전과 동일한 이름입니다."),
    CLUB_BULLETINS_FULL(409, "클럽내 게시판 개수가 최대입니다."),
    BULLETIN_NAME_DUPLICATED(409, "이미 존재하는 게시판 이름입니다."),
    CODE_INCORRECT(409, "일치하지 않은 코드입니다."),
    ALREADY_SUBMITTED_MATCH(409, "해당 경기는 이미 성적이 반영되었습니다."),
    ALREADY_CLUB_HAS_RECORD(409, "해당클럽은 이미 기록을 저장하고 있습니다."),
    ALREADY_SENT_EMAIL_CODE(409, "이미 인증번호를 전송한 이메일입니다."),
    NOT_ENOUGH_POINT(409, "포인트가 부족합니다."),
    SAME_AS_HOST_CLUB(409, "경기 주최클럽과 동일한 클럽은 상대가 될 수 없습니다."),
    ALREADY_MATCH_APPROVED(409, "이미 성사된 경기입니다."),
    ALREADY_PAID_CLUB_MONTHLY_FEE(409, "이미 클럽에 월회비를 납부했습니다."),
    ALREADY_JOINED_CLUB(409, "이미 해당 클럽에 가입신청했습니다."),
    ALREADY_JOINED_SOCCER_MATCH(409, "이미 해당 경기에 참가신청했습니다."),
    NICKNAME_DUPLICATED(409, "이미 존재하는 닉네임입니다"),
    CLUB_NAME_DUPLICATED(409, "이미 존재하는 클럽 이름입니다"),
    CLUB_MEMBER_DUPLICATED(409, "이미 존재하는 클럽 회원입니다"),
    ID_DUPLICATED(409, "이미 존재하는 아이디입니다."),
    EMAIL_DUPLICATED(409, "이미 존재하는 이메일입니다."),
    PASSWORD_SAME(409, "새로운 비밀번호가 현재 비밀번호와 같습니다.");

    private int status;
    private String exceptionMessage;
}
