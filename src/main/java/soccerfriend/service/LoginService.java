package soccerfriend.service;

import static soccerfriend.controller.MemberController.*;

public interface LoginService {

    public static final String LOGIN_MEMBER = "loginMember";

    /**
     * 로그인을 수행합니다.
     *
     * @param loginForm loginId, password를 포함하는 객체
     */
    public void login(LoginRequest loginForm);


    /**
     * 로그아웃을 수행합니다.
     */
    public void logout();
}
