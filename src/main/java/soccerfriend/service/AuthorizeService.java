package soccerfriend.service;

import soccerfriend.controller.MemberController;

public interface AuthorizeService {

    public static final String LOGIN_MEMBER = "loginMember";

    /**
     * 로그인 되어 있는 member의 memberId를 반환합니다.
     *
     * @return 로그인 되어 있는 member의 memberId
     */
    public String getMemberId();

    /**
     * 로그인을 수행합니다.
     *
     * @param loginForm loginId, password를 포함하는 객체
     */
    public void login(MemberController.LoginRequest loginForm);

    /**
     * 로그아웃을 수행합니다.
     */
    public void logout();
}
