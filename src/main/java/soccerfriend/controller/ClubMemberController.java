package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.ClubMemberService;


import static soccerfriend.exception.ExceptionCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/club-members")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;
    private final AuthorizeService authorizeService;

    /**
     * club에 신청한 member를 승인합니다.
     * @param clubId 신청하려는 club의 id
     * @param id clubMember의 id
     */
    @PatchMapping("/approve/{clubId}/{id}")
    public void approve(@PathVariable int clubId, @PathVariable int id) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        clubMemberService.approve(id);
    }
}