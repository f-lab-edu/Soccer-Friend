package soccerfriend.service;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

import static soccerfriend.service.LoginService.LOGIN_MEMBER;

@RequiredArgsConstructor
public class SessionAuthorizeService implements AuthorizeService {
    private HttpSession httpSession;

    @Override
    public String getMemberId() {
        return (String) httpSession.getAttribute(LOGIN_MEMBER);
    }
}
