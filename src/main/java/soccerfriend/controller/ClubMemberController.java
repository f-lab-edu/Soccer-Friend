package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.ClubMember;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.ClubMemberService;

import java.util.List;

import static soccerfriend.exception.ExceptionCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/club-members")
public class ClubMemberController {

    private final ClubMemberService clubMemberService;
    private final AuthorizeService authorizeService;

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