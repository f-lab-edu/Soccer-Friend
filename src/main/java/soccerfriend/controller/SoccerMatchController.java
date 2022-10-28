package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Club;
import soccerfriend.dto.ClubMember;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.ClubMemberService;
import soccerfriend.service.ClubService;
import soccerfriend.service.SoccerMatchRecruitmentService;

import java.util.List;

import static soccerfriend.exception.ExceptionCode.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/soccer-matches")
public class SoccerMatchController {

    private final ClubService clubService;
    private final ClubMemberService clubMemberService;
    private final SoccerMatchRecruitmentService soccerMatchRecruitmentService;
    private final AuthorizeService authorizeService;


    /**
     * 축구경기 모집공고를 게시합니다.
     *
     * @param clubId 경기모집 공고를 게시한 club의 id
     * @param soccerMatchRecruitment 경기에 관한 기본정보
     */
    @PostMapping("/club/{clubId}/create")
    public void create(@PathVariable int clubId, @RequestBody SoccerMatchRecruitment soccerMatchRecruitment) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchRecruitmentService.create(clubId, soccerMatchRecruitment);
    }
}