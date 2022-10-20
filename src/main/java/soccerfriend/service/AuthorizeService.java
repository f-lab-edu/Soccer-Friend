package soccerfriend.service;

import static soccerfriend.utility.InputForm.*;

public interface AuthorizeService {


    /**
     * 로그인 되어 있는 member의 memberId를 반환합니다.
     *
     * @return 로그인 되어 있는 member의 memberId
     */
    public String getMemberId();

    /**
     * 로그인 되어 있는 stadiumOwner의 stadiumOwnerId를 반환합니다.
     *
     * @return 로그인 되어 있는 stadiumOwner의 stadiumOwnerId
     */
    String getStadiumOwnerId();

    /**
     * member의 로그인을 수행합니다.
     *
     * @param loginForm id, password를 포함하는 객체
     */
    public void memberLogin(LoginRequest loginForm);

    /**
     * stadiumOwner의 로그인을 수행합니다.
     *
     * @param loginRequest id, password를 포함하는 객체
     */
    void stadiumOwnerLogin(LoginRequest loginRequest);

    /**
     * 로그아웃을 수행합니다.
     */
    public void logout();
}
