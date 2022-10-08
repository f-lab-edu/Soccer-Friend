package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.LoginForm;
import soccerfriend.dto.Member;
import soccerfriend.service.LoginService;
import soccerfriend.service.MemberService;

import javax.servlet.http.HttpSession;

import static soccerfriend.service.LoginService.LOGIN_MEMBER;
import static utility.HttpStatusCode.CONFLICT;
import static utility.HttpStatusCode.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping("")
    public int signUp(@RequestBody Member member) {
        return memberService.signUp(member);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginForm loginForm) {
        return loginService.login(loginForm);
    }

    @GetMapping("/logout")
    public void logout() {
        loginService.logout();
    }

    @DeleteMapping("")
    public void deleteAccount(HttpSession httpSession) {
        String loginId = (String) httpSession.getAttribute(LOGIN_MEMBER);
        memberService.delete(loginId);
    }

    @GetMapping("/exist/loginId/{loginId}")
    public ResponseEntity<Void> isLoginIdExist(@PathVariable String loginId) {
        if (memberService.isLoginIdExist(loginId)) {
            return CONFLICT;
        }
        return OK;
    }

    @GetMapping("/exist/nickname/{nickname}")
    public ResponseEntity<Void> isNicknameExist(@PathVariable String nickname) {
        if (memberService.isNicknameExist(nickname)) {
            return CONFLICT;
        }
        return OK;
    }

    @PutMapping("/soccerinfo/{memberId}/{soccerInfoId}")
    public void signUp(@PathVariable int memberId, @PathVariable int soccerInfoId) {
        memberService.setSoccerInfo(memberId, soccerInfoId);
    }
}

