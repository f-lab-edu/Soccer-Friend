package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.controller.MemberController;
import soccerfriend.dto.Member;
import soccerfriend.exception.member.IdNotExistException;
import soccerfriend.exception.member.PasswordIncorrectException;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static soccerfriend.exception.ExceptionCode.ID_NOT_EXIST;
import static soccerfriend.exception.ExceptionCode.PASSWORD_INCORRECT;

@RequiredArgsConstructor
@Service
public class SessionAuthorizeService implements AuthorizeService {

    private final HttpSession httpSession;
    private final MemberService memberService;

    @Override
    public String getMemberId() {
        return (String) httpSession.getAttribute(LOGIN_MEMBER);
    }

    /**
     * 로그인을 수행합니다.
     *
     * @param loginForm memberId, password를 포함하는 객체
     * @return 로그인 성공 여부
     */
    @Override
    public void login(MemberController.LoginRequest loginForm) {

        if (!memberService.isMemberIdExist(loginForm.getMemberId())) {
            throw new IdNotExistException(ID_NOT_EXIST);
        }

        Optional<Member> member = memberService.getMemberByLoginIdAndPassword(loginForm.getMemberId(), loginForm.getPassword());

        if (!member.isPresent()) {
            throw new PasswordIncorrectException(PASSWORD_INCORRECT);
        }

        httpSession.setAttribute(LOGIN_MEMBER, loginForm.getMemberId());
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
