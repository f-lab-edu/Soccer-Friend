package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Member;
import soccerfriend.service.MemberService;

import static utility.HttpStatusCode.CONFLICT;
import static utility.HttpStatusCode.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService service;

    @PostMapping("")
    public int signUp(@RequestBody Member member) {
        return service.signUp(member);
    }

    @GetMapping("/exist/loginId/{loginId}")
    public ResponseEntity<Void> isLoginIdExist(@PathVariable String loginId) {
        if (service.isLoginIdExist(loginId)) {
            return CONFLICT;
        }
        return OK;
    }

    @GetMapping("/exist/nickname/{nickname}")
    public ResponseEntity<Void> isNicknameExist(@PathVariable String nickname) {
        if (service.isNicknameExist(nickname)) {
            return CONFLICT;
        }
        return OK;
    }

    @PutMapping("/soccerinfo/{memberId}/{soccerInfoId}")
    public void signUp(@PathVariable int memberId, @PathVariable int soccerInfoId) {
        service.setSoccerInfo(memberId, soccerInfoId);
    }
}

