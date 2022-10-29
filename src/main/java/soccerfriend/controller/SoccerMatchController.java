package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.SoccerMatch;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.ClubMemberService;
import soccerfriend.service.SoccerMatchRecruitmentService;
import soccerfriend.service.SoccerMatchService;
import soccerfriend.utility.InputForm;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/soccer-matches")
public class SoccerMatchController {

    private final ClubMemberService clubMemberService;
    private final SoccerMatchService soccerMatchService;
    private final SoccerMatchRecruitmentService soccerMatchRecruitmentService;
    private final AuthorizeService authorizeService;


    /**
     * 축구경기 모집공고를 게시합니다.
     *
     * @param clubId                 경기모집 공고를 게시한 club의 id
     * @param soccerMatchRecruitment 경기에 관한 기본정보
     */
    @PostMapping("/soccer-match-recruitment")
    public void create(@RequestParam int clubId, @RequestBody SoccerMatchRecruitment soccerMatchRecruitment) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchRecruitmentService.create(clubId, soccerMatchRecruitment);
    }

    /**
     * 특정 id의 soccerMatchRecruitment를 조회합니다.
     *
     * @param soccerMatchRecruitmentId soccerMatchRecruitment의 id
     * @return 특정 id의 soccerMatchRecruitment 객체
     */
    @GetMapping("/soccer-match-recruitment/{soccerMatchRecruitmentId}")
    public SoccerMatchRecruitment getSoccerMatchRecruitmentById(@PathVariable int soccerMatchRecruitmentId) {
        return soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(soccerMatchRecruitmentId);
    }

    /**
     * 특정 club의 모든 soccerMatchRecruitment를 조회합니다.
     *
     * @param clubId club의 id
     * @return 특정 club이 참여한 모든 soccerMatchRecruitment
     */
    @GetMapping("/club/{clubId}/soccer-match-recruitments")
    public List<SoccerMatchRecruitment> getSoccerMatchRecruitmentByClubId(@PathVariable int clubId) {
        return soccerMatchRecruitmentService.getSoccerMatchRecruitmentByClubId(clubId);
    }

    /**
     * soccerMatchRecruitment의 정보를 수정합니다.
     *
     * @param soccerMatchRecruitmentId soccerMatchRecruitment의 id
     * @param request                  수정하고자 하는 값들
     */
    @PatchMapping("/soccer-match-recruitment/{soccerMatchRecruitmentId}")
    public void update(@PathVariable int soccerMatchRecruitmentId, InputForm.UpdateSoccerMatchRecruitmentRequest request) {
        int memberId = authorizeService.getMemberId();
        int clubId = soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(soccerMatchRecruitmentId).getClub1Id();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchRecruitmentService.update(soccerMatchRecruitmentId, request);
    }

    /**
     * soccerMatchRecruitment를 보고 다른 클럽이 이를 승낙합니다. 즉 결투를 신청합니다.
     *
     * @param soccerMatchRecruitmentId soccerMatchRecruitment의 id
     * @param clubId                   신청하려는 club의 id
     */
    @PatchMapping("/soccer-match-recruitment/{soccerMatchRecruitmentId}//approve")
    public void approve(@PathVariable int soccerMatchRecruitmentId, @RequestParam("clubId") int clubId) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchRecruitmentService.setClub2Id(soccerMatchRecruitmentId, clubId);
    }

    /**
     * 경기모집공고를 통해 예정된 경기를 시작합니다. 이 때 soccerMatch가 생성됩니다.
     *
     * @param soccerMatchRecruitmentId 경기모집공고의 id
     */
    @PostMapping("/soccer-match-recruitment/{soccerMatchRecruitmentId}/start")
    public void startMatch(@PathVariable int soccerMatchRecruitmentId) {
        int memberId = authorizeService.getMemberId();
        SoccerMatchRecruitment soccerMatchRecruitment = soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(soccerMatchRecruitmentId);
        int club1Id = soccerMatchRecruitment.getClub1Id();
        int club2Id = soccerMatchRecruitment.getClub2Id();

        if (!clubMemberService.isClubLeaderOrStaff(club1Id, memberId) && !clubMemberService.isClubLeaderOrStaff(club2Id, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchService.create(soccerMatchRecruitmentId);
    }

    /**
     * 특정 id의 soccerMatch를 반환합니다.
     *
     * @param soccerMatchId soccerMatch의 id
     * @return 특정 id의 soccerMatch
     */
    @GetMapping("/{soccerMatchId}")
    public SoccerMatch getSoccerMatchById(@PathVariable int soccerMatchId) {
        return soccerMatchService.getSoccerMatchById(soccerMatchId);
    }

    /**
     * 특정 club이 참여한 모든 soccerMatch를 반환합니다.
     *
     * @param clubId club의 id
     * @return 특정 club이 참여한 모든 soccerMatch
     */
    @GetMapping("/club/{clubId}")
    public List<SoccerMatch> getSoccerMatchByClubId(@PathVariable int clubId) {
        return soccerMatchService.getSoccerMatchByClubId(clubId);
    }
}