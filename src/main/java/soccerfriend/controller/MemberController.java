package soccerfriend.controller;

import lombok.*;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Member;
import soccerfriend.service.LoginService;
import soccerfriend.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;


    @PostMapping
    public int signUp(@RequestBody Member member) {
        return memberService.signUp(member);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginForm) {
        loginService.login(loginForm);
    }

    @GetMapping("/logout")
    public void logout() {
        loginService.logout();
    }

    @DeleteMapping
    public void deleteAccount() {
        memberService.delete();
    }

    @GetMapping("/exist/login-id")
    public boolean isLoginIdExist(@RequestParam String loginId) {
        boolean isDuplicated = memberService.isLoginIdExist(loginId);

        return isDuplicated;
    }

    @PatchMapping("/soccer-info")
    public void setSoccerInfo(@RequestParam int soccerInfoId) {

        memberService.setSoccerInfo(soccerInfoId);
    }

    @PatchMapping("/nickname")
    public void updateNickname(@RequestParam String nickname) {
        memberService.updateNickname(nickname);
    }

    @PatchMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest passwordForm) {
        memberService.updatePassword(passwordForm);
    }

    @Getter
    @Setter
    public static class UpdatePasswordRequest {
        private String before;
        private String after;
    }

    @Getter
    @Setter
    public static class LoginRequest {
        String loginId;
        String password;
    }
}