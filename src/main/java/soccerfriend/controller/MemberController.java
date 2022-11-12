package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Member;
import soccerfriend.service.LoginService;
import soccerfriend.service.MemberService;
import soccerfriend.utility.InputForm.LoginRequest;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;

import static soccerfriend.utility.HttpStatusCode.CONFLICT;
import static soccerfriend.utility.HttpStatusCode.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    /**
     * member 회원가입을 수행합니다.
     *
     * @param member memberId, password, nickname, positionsId, addressId를 가진 member 객체
     */
    @PostMapping
    public void signUp(@RequestBody Member member) {
        memberService.signUp(member);
    }

    /**
     * member의 로그인을 수행합니다.
     *
     * @param loginRequest id, password
     */
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        loginService.memberLogin(loginRequest);
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
    @DeleteMapping("/delete")
    public void deleteAccount() {
        int id = loginService.getMemberId();
        memberService.deleteAccount(id);
    }

    /**
     * member의 중복된 memberId가 있는지 확인합니다.
     *
     * @param memberId
     * @return (200 : 중복되지 않음, 409 : 중복됨)
     */
    @GetMapping("/exist/{memberId}")
    public ResponseEntity<Void> isMemberIdExist(@PathVariable String memberId) {
        boolean isDuplicated = memberService.isMemberIdExist(memberId);

        if (isDuplicated) {
            return CONFLICT;
        }
        return OK;
    }

    /**
     * member의 nickname을 수정합니다.
     *
     * @param nickname
     */
    @PatchMapping("/nickname")
    public void updateNickname(@RequestParam String nickname) {
        int memberId = loginService.getMemberId();
        memberService.updateNickname(memberId, nickname);
    }

    /**
     * member의 pasword를 수정합니다.
     *
     * @param passwordRequest 기존 password, 새로운 password
     */
    @PatchMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest passwordRequest) {
        int memberId = loginService.getMemberId();
        memberService.updatePassword(memberId, passwordRequest);
        loginService.logout();
    }

    /**
     * member의 point를 감소시킵니다.
     *
     * @param point 감소시키고자 하는 point의 양
     */
    @PostMapping("/point/decrease")
    public void decreasePoint(@RequestParam int point) {
        int memberId = loginService.getMemberId();
        memberService.decreasePoint(memberId, point);
    }

    /**
     * 이메일 인증을 위한 인증메일을 인증코드와 함께 전송합니다.
     *
     * @param email 인증하려는 email
     */
    @PostMapping("/email/send-code")
    public void sendEmailAuthenticationCode(@RequestParam String email) {
        memberService.emailAuthentication(email);
    }

    /**
     * 이메일 인증코드를 확인합니다.
     *
     * @param email 인증하려는 email
     * @param code  인증코드
     * @return 인증코드 일치여부
     */
    @PostMapping("/email/check-code")
    public boolean checkEmailAuthenticationCode(@RequestParam String email, @RequestParam String code) {
        return memberService.approveEmail(email, code);
    }

    /**
     * 이메일 인증코드를 확인합니다.
     *
     * @param email 인증하려는 email
     * @param code  인증코드
     * @return 인증코드 일치여부
     */
    @PostMapping("email/check-code/find-id")
    public boolean findMemberId(@RequestParam String email, @RequestParam String code) {
        return memberService.sendMemberId(email, code);
    }


}