package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soccerfriend.dto.Club;
import soccerfriend.dto.ClubMember;
import soccerfriend.dto.Member;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.exception.exception.NotExistException;
import soccerfriend.mapper.ClubMapper;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubMapper clubMapper;
    private final ClubMemberService clubMemberService;
    private final MemberService memberService;

    /**
     * 클럽을 생성합니다.
     *
     * @param memberId 클럽을 생성하는 member의 id
     * @param club     생성하려는 club의 기본정보
     */
    public void create(int memberId, Club club) {

        if (isNameExist(club.getName())) {
            throw new DuplicatedException(CLUB_NAME_DUPLICATED);
        }

        Club oldClub = Club.builder()
                           .name(club.getName())
                           .leader(memberId)
                           .addressId(club.getAddressId())
                           .point(0)
                           .monthlyFee(club.getMonthlyFee())
                           .build();

        clubMapper.insert(oldClub);

        Club newClub = clubMapper.getClubByName(club.getName());

        clubMemberService.addLeader(newClub.getId(), memberId);
    }

    /**
     * 클럽에 가입합니다.
     *
     * @param clubId   가입하려는 club의 id
     * @param memberId 클럽을 생성하는 member의 id
     */
    public void join(int clubId, int memberId) {
        Member member = memberService.getMemberById(memberId);

        if (clubMemberService.isApplied(clubId, memberId)) {
            throw new BadRequestException(ALREADY_JOINED_CLUB);
        }
        if (member.getPoint() < getClubById(clubId).getMonthlyFee()) {
            throw new BadRequestException(NOT_ENOUGH_POINT);
        }

        clubMemberService.add(clubId, memberId);
    }

    /**
     * 특정 id의 club을 반환합니다.
     *
     * @param id club의 id
     * @return 특정 id의 club 객체
     */
    public Club getClubById(int id) {
        return clubMapper.getClubById(id);
    }

    /**
     * club의 name이 존재하는지 확인합니다.
     *
     * @param name 존재 유무를 확인하려는 name
     * @return name 존재 유무(true: 있음, false: 없음)
     */
    public boolean isNameExist(String name) {
        return clubMapper.isNameExist(name);
    }

    /**
     * club의 id가 존재하는지 확인합니다.
     *
     * @param id 존재 유무를 확인하려는 id
     * @return id 존재 유무(true: 있음, false: 없음)
     */
    public boolean isIdExist(int id) {
        return clubMapper.isIdExist(id);
    }

    /**
     * club의 name을 변경합니다.
     *
     * @param id   변경하고자 하는 club의 id
     * @param name 새로 변경할 name
     */
    public void updateName(int id, String name) {
        if (isNameExist(name)) {
            throw new DuplicatedException(CLUB_NAME_DUPLICATED);
        }

        clubMapper.updateName(id, name);
    }

    /**
     * club의 addressId를 변경합니다.
     *
     * @param id        변경하고자 하는 club의 id
     * @param addressId 새로 변경할 addressId
     */
    public void updateAddressId(int id, int addressId) {
        clubMapper.updateAddressId(id, addressId);
    }

    /**
     * club의 monthlyFee를 변경합니다.
     *
     * @param id         변경하고자 하는 club의 id
     * @param monthlyFee 새로 변경할 monthlyFee
     */
    public void updateMonthlyFee(int id, int monthlyFee) {
        clubMapper.updateMonthlyFee(id, monthlyFee);
    }

    /**
     * club의 point를 증가시킵니다.
     *
     * @param id    club의 id
     * @param point 증가시키고자 하는 point의 양
     */
    public void increasePont(int id, int point) {
        clubMapper.increasePoint(id, point);
    }

    /**
     * club의 point를 감소시킵니다.
     *
     * @param id    club의 id
     * @param point 감소시키고자 하는 point의 양
     */
    public void decreasePoint(int id, int point) {
        clubMapper.decreasePoint(id, point);
    }

    @Transactional
    public void approveClubMember(int clubMemberId) {
        ClubMember clubMember = clubMemberService.getClubMemberById(clubMemberId);
        Club club = getClubById(clubMember.getClubId());
        Member member = memberService.getMemberById(clubMember.getMemberId());
        int monthlyFee = club.getMonthlyFee();

        if (member.getPoint() < club.getMonthlyFee()) {
            throw new BadRequestException(NOT_ENOUGH_POINT);
        }

        memberService.decreasePoint(member.getId(), monthlyFee);
        increasePont(club.getId(), monthlyFee);

        clubMemberService.approve(clubMemberId);
    }
}
