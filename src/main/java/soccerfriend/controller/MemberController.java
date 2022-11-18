package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import soccerfriend.aop.MemberLoginCheck;
import soccerfriend.dto.Member;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.service.LoginService;
import soccerfriend.service.MemberService;
import soccerfriend.utility.InputForm.LoginRequest;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;

import static soccerfriend.exception.ExceptionInfo.EMAIL_DUPLICATED;
import static soccerfriend.exception.ExceptionInfo.EMAIL_NOT_EXIST;
import static soccerfriend.utility.HttpStatusCode.CONFLICT;
import static soccerfriend.utility.HttpStatusCode.OK;
import static soccerfriend.utility.PasswordWarning.NO_WARNING;

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
    public void signUp(@Validated @RequestBody Member member) {
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
    @MemberLoginCheck
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
    @MemberLoginCheck
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
    @MemberLoginCheck
    @PatchMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest passwordRequest) {
        int memberId = loginService.getMemberId();
        memberService.updatePasswordByRequest(memberId, passwordRequest);
        memberService.setPasswordWarning(memberId, NO_WARNING);
        loginService.logout();
    }

    /**
     * 문제있는 member의 pasword를 수정합니다.
     *
     * @param passwordRequest 기존 password, 새로운 password
     */
    @PatchMapping("/password/warning")
    public void updateWarningPassword(@RequestBody UpdatePasswordRequest passwordRequest) {
        int memberId = loginService.getMemberId();
        memberService.updatePasswordByRequest(memberId, passwordRequest);
        loginService.logout();
    }

    /**
     * 회원가입을 위한 이메일 인증을 위한 인증메일을 인증코드와 함께 전송합니다.
     *
     * @param email 인증하려는 email
     */
    @PostMapping("/email/send-code/sign-up")
    public void sendEmailAuthenticationCodeForSignUp(@RequestParam String email) {
        if (memberService.isEmailExist(email)) {
            throw new BadRequestException(EMAIL_DUPLICATED);
        }

        memberService.emailAuthentication(email);
    }

    /**
     * 아이디 및 비밀번호 찾기 시 이메일 인증을 위한 인증메일을 인증코드와 함께 전송합니다.
     *
     * @param email 인증하려는 email
     */
    @PostMapping("/email/send-code/find")
    public void sendEmailAuthenticationCodeForFinding(@RequestParam String email) {
        if (!memberService.isEmailExist(email)) {
            throw new BadRequestException(EMAIL_NOT_EXIST);
        }

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
     * 아이디 찾기를 위한 이메일 인증절차를 진행합니다.
     *
     * @param email 인증하려는 email
     * @param code  인증코드
     */
    @PostMapping("email/check-code/find-id")
    public void findMemberId(@RequestParam String email, @RequestParam String code) {
        memberService.sendMemberId(email, code);
    }

    /**
     * 비밀번호 찾기를 위한 이메일 인증절차를 진행합니다.
     *
     * @param email 인증하려는 email
     * @param code  인증코드
     */
    @PostMapping("/email/check-code/find-password")
    public void findPassword(@RequestParam String email, @RequestParam String code) {
        memberService.sendTemporaryPassword(email, code);
    }
}