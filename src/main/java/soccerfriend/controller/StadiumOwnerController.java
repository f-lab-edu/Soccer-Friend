package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.StadiumOwner;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.StadiumOwnerService;
import utility.InputForm.LoginRequest;

import static utility.HttpStatusCode.CONFLICT;
import static utility.HttpStatusCode.OK;

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
     * member의 중복된 memberId가 있는지 확인합니다.
     *
     * @param stadiumOwnerId
     * @return (200 : 중복되지 않음, 409 : 중복됨)
     */
    @GetMapping("/exist/{stadiumOwnerId}")
    public ResponseEntity<Void> isMemberIdExist(@PathVariable String stadiumOwnerId) {
        boolean isDuplicated = stadiumOwnerService.isStadiumOwnerExist(stadiumOwnerId);

        if (isDuplicated) {
            return CONFLICT;
        }
        return OK;
    }
}