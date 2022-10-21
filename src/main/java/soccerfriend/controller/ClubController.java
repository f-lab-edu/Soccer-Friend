package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Club;
import soccerfriend.dto.Member;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.ClubService;
import soccerfriend.service.MemberService;
import soccerfriend.utility.InputForm.LoginRequest;
import soccerfriend.utility.InputForm.UpdatePasswordRequest;

import static soccerfriend.utility.HttpStatusCode.CONFLICT;
import static soccerfriend.utility.HttpStatusCode.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;
    private final AuthorizeService authorizeService;

    /**
     * club을 생성합니다.
     *
     * @param club 기본 정보를 포함한 club 객체
     */
    @PostMapping
    public void create(@RequestBody Club club) {
        int id = authorizeService.getMemberId();
        clubService.create(id, club);
    }

}