package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.StadiumOwner;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.StadiumOwnerService;
import soccerfriend.utility.InputForm.LoginRequest;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;
import soccerfriend.utility.InputForm.UpdateStadiumOwnerRequest;

import static soccerfriend.utility.HttpStatusCode.CONFLICT;
import static soccerfriend.utility.HttpStatusCode.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stadium-owners")
public class StadiumOwnerController {

    private final StadiumOwnerService stadiumOwnerService;
    private final AuthorizeService authorizeService;

    /**
     * stadiumOwner의 회원가입을 수행합니다.
     *
     * @param stadiumOwner stadiumOwnerId, password, representative, companyName, address, taxpayerId, accountBankId, accountNumber를 가진 StadiumOwner 객체
     */
    @PostMapping
    public void signUp(@RequestBody StadiumOwner stadiumOwner) {
        stadiumOwnerService.signUp(stadiumOwner);
    }

    /**
     * stadiumOwner의 로그인을 수행합니다.
     *
     * @param loginRequest id, password
     */
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        authorizeService.stadiumOwnerLogin(loginRequest);
    }

    /**
     * stadiumOwner의 로그아웃을 수행합니다.
     */
    @GetMapping("/logout")
    public void logout() {
        authorizeService.logout();
    }

    /**
     * stadiumOwner의 탈퇴를 수행합니다.
     */
    @DeleteMapping
    public void deleteAccount() {
        String stadiumOwnerId = authorizeService.getStadiumOwnerId();
        stadiumOwnerService.deleteAccount(stadiumOwnerId);
    }

    /**
     * member의 중복된 memberId가 있는지 확인합니다.
     *
     * @param stadiumOwnerId
     * @return (200 : 중복되지 않음, 409 : 중복됨)
     */
    @GetMapping("/exist/{stadiumOwnerId}")
    public ResponseEntity<Void> isStadiumOwnerIdExist(@PathVariable String stadiumOwnerId) {
        boolean isDuplicated = stadiumOwnerService.isStadiumOwnerExist(stadiumOwnerId);

        if (isDuplicated) {
            return CONFLICT;
        }
        return OK;
    }

    /**
     * stadiumOwner의 정보를 변경합니다. (비밀번호, 아이디, 포인트 제외)
     *
     * @param stadiumOwnerRequest
     */
    @PatchMapping
    public void updateStadiumOwner(@RequestBody UpdateStadiumOwnerRequest stadiumOwnerRequest) {
        String stadiumOwnerId = authorizeService.getStadiumOwnerId();
        stadiumOwnerService.updateStadiumOwner(stadiumOwnerId, stadiumOwnerRequest);
        authorizeService.logout();
    }

    /**
     * stadiumOwner의 pasword를 수정합니다.
     *
     * @param passwordRequest 기존 password, 새로운 password
     */
    @PatchMapping("/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest passwordRequest) {
        String stadiumOwnerId = authorizeService.getStadiumOwnerId();
        stadiumOwnerService.updatePassword(stadiumOwnerId, passwordRequest);
        authorizeService.logout();
    }
}