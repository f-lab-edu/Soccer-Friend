package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.ClubMemberService;
import soccerfriend.service.SoccerMatchRecruitmentService;
import soccerfriend.utility.InputForm;

import java.util.List;

import static soccerfriend.exception.ExceptionCode.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/soccer-matches")
public class SoccerMatchController {

    private final ClubMemberService clubMemberService;
    private final SoccerMatchRecruitmentService soccerMatchRecruitmentService;
    private final AuthorizeService authorizeService;


    /**
     * 축구경기 모집공고를 게시합니다.
     *
     * @param clubId                 경기모집 공고를 게시한 club의 id
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

    /**
     * 특정 id의 soccerMatchRecruitment를 조회합니다.
     *
     * @param id soccerMatchRecruitment의 id
     * @return 특정 id의 soccerMatchRecruitment 객체
     */
    @GetMapping("/{id}")
    public SoccerMatchRecruitment getSoccerMatchRecruitmentById(@PathVariable int id) {
        return soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(id);
    }

    /**
     * 특정 club의 모든 soccerMatchRecruitment를 조회합니다.
     *
     * @param clubId club의 id
     * @return 특정 club이 참여한 모든 soccerMatchRecruitment
     */
    @GetMapping("/club/{clubId}/all")
    public List<SoccerMatchRecruitment> getSoccerMatchRecruitmentByClubId(@PathVariable int clubId) {
        return soccerMatchRecruitmentService.getSoccerMatchRecruitmentByClubId(clubId);
    }

    /**
     * soccerMatchRecruitment의 정보를 수정합니다.
     *
     * @param id      soccerMatchRecruitment의 id
     * @param request 수정하고자 하는 값들
     */
    @PatchMapping("/{id}")
    public void update(@PathVariable("id") int id, InputForm.UpdateSoccerMatchRecruitmentRequest request) {
        int memberId = authorizeService.getMemberId();
        int clubId = soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(id).getClub1Id();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchRecruitmentService.update(id, request);
    }

    /**
     * soccerMatchRecruitment를 보고 다른 클럽이 이를 승낙합니다. 즉 결투를 신청합니다.
     *
     * @param id soccerMatchRecruitment의 id
     * @param clubId 신청하려는 club의 id
     */
    @PatchMapping("/{id}/club/{clubId}/approve")
    public void approve(@PathVariable("id") int id, @PathVariable("clubId") int clubId) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchRecruitmentService.setClub2Id(id, clubId);
    }
}