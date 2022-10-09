package soccerfriend.controller;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Member;
import soccerfriend.service.LoginService;
import soccerfriend.service.MemberService;

import javax.servlet.http.HttpSession;

import static soccerfriend.service.LoginService.LOGIN_MEMBER;
import static soccerfriend.utility.HttpStatusCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final HttpSession httpSession;

    @PostMapping("")
    public int signUp(@RequestBody Member member) {
        return memberService.signUp(member);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginForm loginForm) {
        boolean result = loginService.login(loginForm);

        if (!result) return BAD_REQUEST;
        return OK;
    }

    @GetMapping("/logout")
    public void logout() {
        loginService.logout();
    }

    @DeleteMapping
    public void deleteAccount() {
        String loginId = (String) httpSession.getAttribute(LOGIN_MEMBER);
        memberService.delete(loginId);
    }

    @GetMapping("/exist/loginId")
    public ResponseEntity<Void> isLoginIdExist(@RequestParam String loginId) {
        boolean isDuplicated = memberService.isLoginIdExist(loginId);

        if (isDuplicated) {
            return CONFLICT;
        }
        return OK;
    }

    @GetMapping("/exist/nickname")
    public ResponseEntity<Void> isNicknameExist(@RequestParam String nickname) {
        boolean isDuplicated = memberService.isNicknameExist(nickname);

        if (isDuplicated) {
            return CONFLICT;
        }
        return OK;
    }

    @PatchMapping("/soccerinfo")
    public void setSoccerInfo(@RequestParam int soccerInfoId) {
        String loginId = (String) httpSession.getAttribute(LOGIN_MEMBER);

        memberService.setSoccerInfo(loginId, soccerInfoId);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<Void> updateNickname(@RequestParam String nickname) {
        String loginId = (String) httpSession.getAttribute(LOGIN_MEMBER);
        boolean result = memberService.updateNickname(loginId, nickname);

        if (!result) {
            return BAD_REQUEST;
        }
        return OK;
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordForm passwordForm) {
        String loginId = (String) httpSession.getAttribute(LOGIN_MEMBER);
        boolean result = memberService.updatePassword(loginId, passwordForm);

        if (!result) {
            return BAD_REQUEST;
        }
        return OK;
    }

    @Getter
    @Setter
    public static class UpdatePasswordForm {
        private String before;
        private String after;
    }

    @Getter
    @Setter
    public static class LoginForm {
        String loginId;
        String password;
    }
}