package soccerfriend.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import soccerfriend.dto.LoginForm;
import soccerfriend.dto.Member;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static utility.HttpStatusCode.*;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final MemberService memberService;
    private final HttpSession httpSession;

    @Override
    public ResponseEntity<Void> login(LoginForm loginForm) {
        Optional<Member> member = memberService.getMemberByLoginIdAndPassword(loginForm.getLoginId(), loginForm.getPassword());

        if (!member.isPresent()) return BAD_REQUEST;

        httpSession.setAttribute(LOGIN_USER, loginForm.getLoginId());
        httpSession.setMaxInactiveInterval(30 * 60);

        return OK;
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }

}
