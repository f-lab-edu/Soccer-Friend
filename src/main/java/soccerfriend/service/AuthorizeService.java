package soccerfriend.service;

import static soccerfriend.controller.MemberController.*;

public interface AuthorizeService {


    /**
     * 로그인 되어 있는 member의 memberId를 반환합니다.
     *
     * @return 로그인 되어 있는 member의 memberId
     */
    public String getMemberId();

    /**
     * member의 로그인을 수행합니다.
     *
     * @param loginForm loginId, password를 포함하는 객체
     */
    public void memberLogin(LoginRequest loginForm);

    /**
     * stadiumOwner의 로그인을 수행합니다.
     *
     * @param loginForm loginId, password를 포함하는 객체
     */
    void stadiumOwnerLogin(LoginRequest loginForm);

    /**
     * 로그아웃을 수행합니다.
     */
    public void logout();
}
