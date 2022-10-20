package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.controller.MemberController.LoginRequest;
import soccerfriend.dto.Member;
import soccerfriend.dto.StadiumOwner;
import soccerfriend.exception.member.NotExistException;
import soccerfriend.exception.member.NotMatchException;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static soccerfriend.exception.ExceptionCode.LOGIN_FORM_INCORRECT;
import static soccerfriend.exception.ExceptionCode.LOGIN_INFO_NOT_EXIST;
import static utility.SessionKey.SESSION_LOGIN_MEMBER;
import static utility.SessionKey.SESSION_LOGIN_STADIUM_OWNER;

@RequiredArgsConstructor
@Service
public class SessionAuthorizeService implements AuthorizeService {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private final StadiumOwnerService stadiumOwnerService;

    @Override
    public String getMemberId() {
        String id = (String) httpSession.getAttribute(SESSION_LOGIN_MEMBER);
        if (id == null) {
            throw new NotExistException(LOGIN_INFO_NOT_EXIST);
        }
        return id;
    }

    /**
     * Member의 로그인을 수행합니다.
     *
     * @param loginForm memberId, password를 포함하는 객체
     * @return 로그인 성공 여부
     */
    @Override
    public void memberLogin(LoginRequest loginForm) {
        Optional<Member> member = memberService.getMemberByLoginIdAndPassword(loginForm.getId(), loginForm.getPassword());

        if (!member.isPresent()) {
            throw new NotMatchException(LOGIN_FORM_INCORRECT);
        }

        httpSession.setAttribute(SESSION_LOGIN_MEMBER, loginForm.getId());
        httpSession.setMaxInactiveInterval(30 * 60);
    }

    /**
     * StadiumOwner의 로그인을 수행합니다.
     *
     * @param loginForm memberId, password를 포함하는 객체
     * @return 로그인 성공 여부
     */
    @Override
    public void stadiumOwnerLogin(LoginRequest loginForm) {
        Optional<StadiumOwner> stadiumOwner = stadiumOwnerService.getStadiumOwnerByStadiumOwnerIdAndPassword(loginForm.getId(), loginForm.getPassword());

        if (!stadiumOwner.isPresent()) {
            throw new NotMatchException(LOGIN_FORM_INCORRECT);
        }

        httpSession.setAttribute(SESSION_LOGIN_STADIUM_OWNER, loginForm.getId());
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
