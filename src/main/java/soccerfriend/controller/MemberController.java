package soccerfriend.controller;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Member;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.MemberService;

import static utility.HttpStatusCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final AuthorizeService authorizeService;

    /**
     * member 회원가입을 수행합니다.
     *
     * @param member memberId, password, nickname, positionsId, addressId를 가진 member 객체
     * @return member의 id
     */
    @PostMapping
    public void signUp(@RequestBody Member member) {
        memberService.signUp(member);
    }

    /**
     * member의 로그인을 수행합니다.
     *
     * @param loginForm memberId, password
     */
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginForm) {
        authorizeService.login(loginForm);
    }

    /**
     * member의 로그아웃을 수행합니다.
     */
    @GetMapping("/logout")
    public void logout() {
        authorizeService.logout();
    }

    /**
     * member의 탈퇴를 수행합니다.
     */
    @DeleteMapping
    public void deleteAccount() {
        String memberId = authorizeService.getMemberId();
        memberService.deleteAccount(memberId);
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
        String memberId = authorizeService.getMemberId();
        memberService.updateNickname(memberId, nickname);
    }

    /**
     * member의 pasword를 수정합니다.
     *
     * @param passwordForm 기존 password, 새로운 password
     */
    @PatchMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest passwordForm) {
        String memberId = authorizeService.getMemberId();
        memberService.updatePassword(memberId, passwordForm);
        authorizeService.logout();
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
        String memberId;
        String password;
    }
}