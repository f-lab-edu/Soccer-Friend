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
     *
     * @param clubId club의 id
     */
    @DeleteMapping("/{clubId}/member-delete")
    public void memberDelete(@PathVariable int clubId) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubMember(clubId, memberId)) {
            throw new NoPermissionException(NOT_CLUB_MEMBER);
        }
        if (clubMemberService.isClubLeader(clubId, memberId)) {
            throw new NoPermissionException(IS_CLUB_LEADER);
        }

        clubMemberService.deleteClubMember(clubId, memberId);
    }

    /**
     * club에 가입신청한 member들의 목록을 반환합니다. approve가 true이면 승인된 맴버, false이면 승인되지 않은 맴버를 반환합니다.
     *
     * @param clubId
     * @return club에 가입한 member들의 목록
     */
    @GetMapping("/{clubId}/club-members")
    public List<ClubMember> clubMember(@PathVariable int clubId, @RequestParam boolean approve) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubMember(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        if(approve){
            return clubMemberService.getClubMembers(clubId);
        }
        return clubMemberService.getNotAcceptedClubMembers(clubId);
    }

    /**
     * club의 name을 변경합니다.
     *
     * @param clubId
     * @param name 새로 변경하고자하는 name
     */
    @PatchMapping("/{clubId}/name")
    public void updateName(@PathVariable int clubId, @RequestParam String name) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        clubService.updateName(clubId, name);
    }

    /**
     * club의 name을 변경합니다.
     *
     * @param clubId
     * @param addressId 새로 변경하고자 하는 addressId
     */
    @PatchMapping("/{clubId}/address-id")
    public void updateAddressId(@PathVariable int clubId, @RequestParam int addressId) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        clubService.updateAddressId(clubId, addressId);
    }

    /**
     * club의 monthlyFee를 변경합니다.
     *
     * @param clubId
     * @param monthlyFee 새로 변경하고자 하는 monthlyFee
     */
    @PatchMapping("/{clubId}/monthly-fee")
    public void updateMonthlyFee(@PathVariable int clubId, @RequestParam int monthlyFee) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        clubService.updateMonthlyFee(clubId, monthlyFee);
    }

    /**
     * club에 신청한 member를 승인합니다.
     *
     * @param clubMemberId clubMember의 id
     */
    @PatchMapping("/club-member/{clubMemberId}/approve")
    public void approve(@PathVariable int clubMemberId) {
        int memberId = authorizeService.getMemberId();
        ClubMember clubMember = clubMemberService.getClubMemberById(clubMemberId);
        int clubId = clubMember.getClubId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        clubMemberService.approveClubMember(clubMemberId);
    }

    /**
     * club에 가입한 member가 월회비를 납부합니다.
     *
     * @param clubId 월회비를 납부하려는 club의 id
     */
    @PatchMapping("/{clubId}/pay/monthly-fee/{year}/{month}")
    public void payMonthlyFee(@PathVariable int clubId, @PathVariable int year, @PathVariable int month) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubMember(clubId, memberId)) {
            throw new NoPermissionException(NOT_CLUB_MEMBER);
        }

        clubService.payMonthlyFee(clubId, memberId, year, month);
    }

    /**
     * payment가 true일 경우 회비를 납부한 회원들을, false일 경우 회비를 납부하지 않은 회원들을 반환합니다.
     *
     * @param clubId club의 id
     * @param payment 회비 납부 여부
     * @return 회비 납부여부에 관한 회원들
     */
    @GetMapping("/{clubId}/club-members")
    public List<ClubMember> getClubMembersByPayment(@PathVariable int clubId, @RequestParam boolean payment) {
        int memberId = authorizeService.getMemberId();
        if (!clubMemberService.isClubLeaderOrStaff(clubId, memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        if(payment){
            return clubMemberService.getPaidClubMembers(clubId);
        }
        return clubMemberService.getNotPaidClubMembers(clubId);
    }
}