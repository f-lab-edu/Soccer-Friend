package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.controller.MemberController;
import soccerfriend.dto.Member;
import soccerfriend.exception.member.NotExistException;
import soccerfriend.exception.member.NotMatchException;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static soccerfriend.exception.ExceptionCode.LOGIN_FORM_INCORRECT;
import static soccerfriend.exception.ExceptionCode.LOGIN_INFO_NOT_EXIST;
import static utility.SessionKey.SESSION_LOGIN_MEMBER;

@RequiredArgsConstructor
@Service
public class SessionAuthorizeService implements AuthorizeService {

    private final HttpSession httpSession;
    private final MemberService memberService;

    @Override
    public String getMemberId() {
        String id = (String) httpSession.getAttribute(SESSION_LOGIN_MEMBER);
        if (id == null) {
            throw new NotExistException(LOGIN_INFO_NOT_EXIST);
        }
        return id;
    }

    /**
     * 로그인을 수행합니다.
     *
     * @param loginForm memberId, password를 포함하는 객체
     * @return 로그인 성공 여부
     */
    @Override
    public void login(MemberController.LoginRequest loginForm) {
        Optional<Member> member = memberService.getMemberByLoginIdAndPassword(loginForm.getMemberId(), loginForm.getPassword());

        if (!member.isPresent()) {
            throw new NotMatchException(LOGIN_FORM_INCORRECT);
        }

        httpSession.setAttribute(SESSION_LOGIN_MEMBER, loginForm.getMemberId());
        httpSession.setMaxInactiveInterval(30 * 60);
    }

    /**
     * 로그아웃을 수행합니다.
     */
    @Override
    public void logout() {
        httpSession.invalidate();
    }
}
