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

    /**
     * member 회원가입을 수행합니다.
     *
     * @param member loginId, password, nickname을 가진 member 객체
     * @return member의 id
     */
    @PostMapping
    public int signUp(@RequestBody Member member) {
        return memberService.signUp(member);
    }

    /**
     * member의 로그인을 수행합니다.
     *
     * @param loginForm memberId, password
     */
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginForm) {
        loginService.login(loginForm);
    }

    /**
     * member의 로그아웃을 수행합니다.
     */
    @GetMapping("/logout")
    public void logout() {
        loginService.logout();
    }

    /**
     * member의 탈퇴를 수행합니다.
     */
    @DeleteMapping
    public void deleteAccount() {
        memberService.delete();
    }

    /**
     * member의 중복된 loginId가 있는지 확인합니다.
     * @param loginId
     * @return 중복여부 (true: 중복, false: 중복아님)
     */
    @GetMapping("/exist/login-id")
    public boolean isLoginIdExist(@RequestParam String loginId) {
        boolean isDuplicated = memberService.isLoginIdExist(loginId);

        return isDuplicated;
    }


    /**
     * member 객체에 soccerInfo를 추가합니다.
     *
     * @param soccerInfoId
     */
    @PatchMapping("/soccer-info")
    public void setSoccerInfo(@RequestParam int soccerInfoId) {

        memberService.setSoccerInfo(soccerInfoId);
    }

    /**
     * member의 nickname을 수정합니다.
     *
     * @param nickname
     */
    @PatchMapping("/nickname")
    public void updateNickname(@RequestParam String nickname) {
        memberService.updateNickname(nickname);
    }

    /**
     * member의 pasword를 수정합니다.
     *
     * @param passwordForm 기존 password, 새로운 password
     */
    @PatchMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest passwordForm) {
        memberService.updatePassword(passwordForm);
    }

    /**
     * password를 변경하기 위해 입력해야 하는 값들
     */
    @Getter
    @Setter
    public static class UpdatePasswordRequest {
        private String before;
        private String after;
    }

    /**
     * login 하기위해 입력해야 하는 값들
     */
    @Getter
    @Setter
    public static class LoginRequest {
        String loginId;
        String password;
    }
}