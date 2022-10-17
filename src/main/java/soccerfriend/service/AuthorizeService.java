package soccerfriend.service;

public interface AuthorizeService {
    /**
     * 로그인 되어 있는 member의 memberId를 반환합니다.
     *
     * @return 로그인 되어 있는 member의 memberId
     */
    public String getMemberId();
}
