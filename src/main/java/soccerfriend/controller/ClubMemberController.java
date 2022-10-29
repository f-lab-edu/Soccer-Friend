package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.ClubMember;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.ClubMemberService;


import static soccerfriend.exception.ExceptionInfo.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/club-members")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;
    private final AuthorizeService authorizeService;

    /**
     * club에 신청한 member를 승인합니다.
     *
     * @param id clubMember의 id
     */
    @PatchMapping("/approve/{id}")
    public void approve( @PathVariable int id) {
        int memberId = authorizeService.getMemberId();
        ClubMember clubMember = clubMemberService.getClubMemberById(id);
        int clubId = clubMember.getClubId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        clubMemberService.approve(id);
    }
}