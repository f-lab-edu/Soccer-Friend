package soccerfriend.controller;

import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Member;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.MemberService;
import soccerfriend.utility.InputForm.LoginRequest;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;

import static soccerfriend.utility.HttpStatusCode.*;

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
        authorizeService.memberLogin(loginRequest);
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
        int id = authorizeService.getMemberId();
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
        int memberId = authorizeService.getMemberId();
        memberService.updateNickname(memberId, nickname);
    }

    /**
     * member의 pasword를 수정합니다.
     *
     * @param passwordRequest 기존 password, 새로운 password
     */
    @PatchMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest passwordRequest) {
        int memberId = authorizeService.getMemberId();
        memberService.updatePassword(memberId, passwordRequest);
        authorizeService.logout();
    }

    /**
     * member의 point를 증가시킵니다.
     *
     * @param point 증가시키고자 하는 point의 양
     */
    @PostMapping("/point/increase")
    public void increasePoint(@RequestParam int point) {
        int memberId = authorizeService.getMemberId();
        memberService.increasePoint(memberId, point);
    }

    /**
     * member의 point를 감소시킵니다.
     *
     * @param point 감소시키고자 하는 point의 양
     */
    @PostMapping("/point/decrease")
    public void decreasePoint(@RequestParam int point) {
        int memberId = authorizeService.getMemberId();
        memberService.decreasePoint(memberId, point);
    }
}