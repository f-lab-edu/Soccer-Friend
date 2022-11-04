package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.SoccerMatch;
import soccerfriend.dto.SoccerMatchMember;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.*;
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
    private final SoccerMatchMemberService soccerMatchMemberService;
    private final AuthorizeService authorizeService;


    /**
     * 축구경기 모집공고를 게시합니다.
     *
     * @param clubId                 경기모집 공고를 게시한 club의 id
     * @param soccerMatchRecruitment 경기에 관한 기본정보
     */
    @PostMapping("/recruitments")
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
    @GetMapping("/recruitments/{soccerMatchRecruitmentId}")
    public SoccerMatchRecruitment getSoccerMatchRecruitmentById(@PathVariable int soccerMatchRecruitmentId) {
        return soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(soccerMatchRecruitmentId);
    }

    /**
     * 특정 club의 모든 soccerMatchRecruitment를 조회합니다.
     *
     * @param clubId club의 id
     * @return 특정 club이 참여한 모든 soccerMatchRecruitment
     */
    @GetMapping("/club/{clubId}/recruitments")
    public List<SoccerMatchRecruitment> getSoccerMatchRecruitmentByClubId(@PathVariable int clubId) {
        return soccerMatchRecruitmentService.getSoccerMatchRecruitmentByClubId(clubId);
    }

    /**
     * soccerMatchRecruitment의 정보를 수정합니다.
     *
     * @param soccerMatchRecruitmentId soccerMatchRecruitment의 id
     * @param request                  수정하고자 하는 값들
     */
    @PatchMapping("/recruitments/{soccerMatchRecruitmentId}")
    public void update(@PathVariable int soccerMatchRecruitmentId, InputForm.UpdateSoccerMatchRecruitmentRequest request) {
        int memberId = authorizeService.getMemberId();
        int clubId = soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(soccerMatchRecruitmentId).getClubAId();
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
    @PatchMapping("/recruitments/{soccerMatchRecruitmentId}/approve")
    public void approve(@PathVariable int soccerMatchRecruitmentId, @RequestParam("clubId") int clubId) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchRecruitmentService.setClubBId(soccerMatchRecruitmentId, clubId);
    }

    /**
     * 경기모집공고를 통해 예정된 경기를 시작합니다. 이 때 soccerMatch가 생성됩니다.
     *
     * @param soccerMatchRecruitmentId 경기모집공고의 id
     */
    @PostMapping("/recruitments/{soccerMatchRecruitmentId}/start")
    public void startMatch(@PathVariable int soccerMatchRecruitmentId) {
        int memberId = authorizeService.getMemberId();
        SoccerMatchRecruitment soccerMatchRecruitment = soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(soccerMatchRecruitmentId);
        int clubAId = soccerMatchRecruitment.getClubAId();
        int clubBId = soccerMatchRecruitment.getClubBId();

        if (!clubMemberService.isClubLeaderOrStaff(clubAId, memberId) && !clubMemberService.isClubLeaderOrStaff(clubBId, memberId)) {
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

    /**
     * 특정 경기에 특정 클럽선수로 참가신청 합니다. 이 때 해당 클럽이 아니어도 참가신청을 할 수 있습니다.
     *
     * @param soccerMatchId soccerMatch의 id
     * @param clubId        선수로 뛰고자 하는 club의 id
     */
    @PostMapping("{soccerMatchId}/apply")
    public void applySoccerMatchMember(@PathVariable int soccerMatchId, @RequestParam int clubId) {
        int memberId = authorizeService.getMemberId();
        soccerMatchMemberService.apply(soccerMatchId, clubId, memberId);
    }

    /**
     * soccerMatch에 참가신청한 선수들을 반환합니다. approved가 true이면 승인된 선수들 false이면 승인 대기중인 선수들을 반환합니다.
     *
     * @param soccerMatchId soccerMatch의 id
     * @param clubId
     * @param approved
     * @return
     */
    @GetMapping("/{soccerMatchId}/club/{clubId}/soccer-match-member")
    public List<SoccerMatchMember> getSoccerMatchMember(@PathVariable int soccerMatchId, @PathVariable int clubId, @RequestParam boolean approved) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        if (approved) {
            return soccerMatchMemberService.getApprovedSoccerMatchMember(soccerMatchId, clubId);
        }
        return soccerMatchMemberService.getNotApprovedSoccerMatchMember(soccerMatchId, clubId);
    }

    /**
     * soccerMatch에 참가신청한 선수를 승인합니다.
     *
     * @param soccerMatchMemberId soccerMatchMember의 id
     */
    @PatchMapping("soccer-match-member/{soccerMatchMemberId}/approve")
    public void approveSoccerMatchMember(@PathVariable int soccerMatchMemberId) {
        int memberId = authorizeService.getMemberId();
        SoccerMatchMember soccerMatchMember = soccerMatchMemberService.getSoccerMatchMemberById(soccerMatchMemberId);
        int clubId = soccerMatchMember.getClubId();

        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        soccerMatchMemberService.approve(soccerMatchMemberId);
    }
}