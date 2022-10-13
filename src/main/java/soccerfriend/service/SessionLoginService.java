package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.controller.MemberController.LoginRequest;
import soccerfriend.dto.Member;
import soccerfriend.exception.member.IdNotExistException;
import soccerfriend.exception.member.PasswordIncorrectException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final MemberService memberService;
    private final HttpSession httpSession;

    /**
     * 로그인을 수행합니다.
     *
     * @param loginForm memberId, password를 포함하는 객체
     * @return 로그인 성공 여부
     */
    @Override
    public void login(LoginRequest loginForm) {

        if(!memberService.isMemberIdExist(loginForm.getMemberId())){
            throw new IdNotExistException();
        }

        Optional<Member> member = memberService.getMemberByLoginIdAndPassword(loginForm.getMemberId(), loginForm.getPassword());

        if (!member.isPresent()){
            throw new PasswordIncorrectException();
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
