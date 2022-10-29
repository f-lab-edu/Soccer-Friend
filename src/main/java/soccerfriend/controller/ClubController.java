package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Club;
import soccerfriend.dto.ClubMember;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.AuthorizeService;
import soccerfriend.service.ClubMemberService;
import soccerfriend.service.ClubService;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;
    private final ClubMemberService clubMemberService;
    private final AuthorizeService authorizeService;

    /**
     * club을 생성합니다.
     *
     * @param club 기본 정보를 포함한 club 객체
     */
    @PostMapping
    public void create(@RequestBody Club club) {
        int memberId = authorizeService.getMemberId();
        clubService.create(memberId, club);
    }

    /**
     * club에 가입신청합니다.
     *
     * @param clubId club의 id
     */
    @PostMapping("/{clubId}/join")
    public void join(@PathVariable int clubId) {
        int memberId = authorizeService.getMemberId();
        clubService.join(clubId, memberId);
    }

    /**
     * club을 탈퇴합니다.
     * @param clubId club의 id
     */
    @DeleteMapping("/{clubId}/member-delete")
    public void memberDelete(@PathVariable int clubId){
        int memberId = authorizeService.getMemberId();
        if(!clubMemberService.isClubMember(clubId, memberId)){
            throw new NoPermissionException(NOT_CLUB_MEMBER);
        }
        if(clubMemberService.isClubLeader(clubId, memberId)){
            throw new NoPermissionException(IS_CLUB_LEADER);
        }

        clubMemberService.deleteClubMember(clubId, memberId);
    }

    /**
     * club에 가입한 member들의 목록을 반환합니다.
     *
     * @param clubId
     * @return club에 가입한 member들의 목록
     */
    @PostMapping("/{clubId}/club-members")
    public List<ClubMember> clubMember(@PathVariable int clubId) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubMember(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        return clubMemberService.getClubMembers(clubId);
    }

    /**
     * club에 신청하고 승인 대기중인 member들의 목록을 반환합니다.
     *
     * @param clubId
     * @return club에 신청하고 승인 대기중인 member들의 목록
     */
    @PostMapping("/{clubId}/club-members?approved=false")
    public List<ClubMember> notAcceptedClubMembers(@PathVariable int clubId) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        return clubMemberService.getNotAcceptedClubMembers(clubId);
    }

    /**
     * club의 name을 변경합니다.
     * @param clubId
     * @param name 새로 변경하고자하는 name
     */
    @PatchMapping("/{clubId}/name")
    public void updateName(@PathVariable int clubId, @RequestParam String name){
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        clubService.updateName(clubId, name);
    }

    /**
     * club의 name을 변경합니다.
     * @param clubId
     * @param addressId 새로 변경하고자 하는 addressId
     */
    @PatchMapping("/{clubId}/address-id")
    public void updateAddressId(@PathVariable int clubId, @RequestParam int addressId){
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        clubService.updateAddressId(clubId, addressId);
    }
}