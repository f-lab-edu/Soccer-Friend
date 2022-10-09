package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.controller.MemberController;
import soccerfriend.dto.Member;

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
     * @param loginForm loginId, password를 포함하는 객체
     * @return 로그인 성공 여부
     */
    @Override
    public boolean login(MemberController.LoginForm loginForm) {
        Optional<Member> member = memberService.getMemberByLoginIdAndPassword(loginForm.getLoginId(), loginForm.getPassword());

        if (!member.isPresent()) return false;

        httpSession.setAttribute(LOGIN_MEMBER, loginForm.getLoginId());
        httpSession.setMaxInactiveInterval(30 * 60);

        return true;
    }

    /**
     * 로그아웃을 수행합니다.
     */
    @Override
    public void logout() {
        httpSession.invalidate();
    }

}
