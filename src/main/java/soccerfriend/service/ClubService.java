package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soccerfriend.dto.Club;
import soccerfriend.dto.ClubMember;
import soccerfriend.dto.Member;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.mapper.ClubMapper;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubMapper mapper;
    private final ClubMemberService clubMemberService;
    private final MemberService memberService;
    private final ClubMonthlyFeeService clubMonthlyFeeService;
    private final ClubSoccerMatchRecordService clubSoccerMatchRecordService;

    /**
     * 클럽을 생성합니다.
     *
     * @param memberId 클럽을 생성하는 member의 id
     * @param club     생성하려는 club의 기본정보
     */
    @Transactional
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
                           .bulletinNum(0)
                           .build();

        mapper.insert(oldClub);
        Club newClub = mapper.getClubByName(club.getName());
        clubMemberService.addLeader(newClub.getId(), memberId);
        clubSoccerMatchRecordService.create(newClub.getId());
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
        return mapper.getClubById(id);
    }

    /**
     * club의 name이 존재하는지 확인합니다.
     *
     * @param name 존재 유무를 확인하려는 name
     * @return name 존재 유무(true: 있음, false: 없음)
     */
    public boolean isNameExist(String name) {
        return mapper.isNameExist(name);
    }

    /**
     * club의 id가 존재하는지 확인합니다.
     *
     * @param id 존재 유무를 확인하려는 id
     * @return id 존재 유무(true: 있음, false: 없음)
     */
    public boolean isIdExist(int id) {
        return mapper.isIdExist(id);
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

        mapper.updateName(id, name);
    }

    /**
     * club의 addressId를 변경합니다.
     *
     * @param id        변경하고자 하는 club의 id
     * @param addressId 새로 변경할 addressId
     */
    public void updateAddressId(int id, int addressId) {
        mapper.updateAddressId(id, addressId);
    }

    /**
     * club의 monthlyFee를 변경합니다.
     *
     * @param id         변경하고자 하는 club의 id
     * @param monthlyFee 새로 변경할 monthlyFee
     */
    public void updateMonthlyFee(int id, int monthlyFee) {
        mapper.updateMonthlyFee(id, monthlyFee);
    }

    /**
     * club의 point를 증가시킵니다.
     *
     * @param id    club의 id
     * @param point 증가시키고자 하는 point의 양
     */
    public void increasePont(int id, int point) {
        mapper.increasePoint(id, point);
    }

    /**
     * club의 point를 감소시킵니다.
     *
     * @param id    club의 id
     * @param point 감소시키고자 하는 point의 양
     */
    public void decreasePoint(int id, int point) {
        mapper.decreasePoint(id, point);
    }

    /**
     * club에 가입한 member가 월회비를 납부합니다.
     *
     * @param clubId   월회비를 납부하려는 club의 id
     * @param memberId 납부하는 member의 id
     */
    @Transactional
    public void payMonthlyFee(int clubId, int memberId, int year, int month) {
        Member member = memberService.getMemberById(memberId);
        ClubMember clubMember = clubMemberService.getClubMemberByClubIdAndMemberId(clubId, memberId);
        Club club = getClubById(clubId);
        int fee = club.getMonthlyFee();
        if (member.getPoint() < fee) {
            throw new BadRequestException(NOT_ENOUGH_POINT);
        }
        if (clubMonthlyFeeService.isAlreadyPaid(clubId, memberId, year, month)) {
            throw new BadRequestException(ALREADY_PAID_CLUB_MONTHLY_FEE);
        }

        memberService.decreasePoint(memberId, fee);
        increasePont(clubId, fee);
        clubMonthlyFeeService.add(clubId, clubMember.getId(), fee, year, month);
    }

    /**
     * club의 게시판 개수를 증가시킵니다.
     *
     * @param id club의 id
     */
    public void increaseBulletinNum(int id) {
        mapper.increaseBulletinNum(id);
    }

    /**
     * club의 게시판 개수를 감소시킵니다.
     *
     * @param id club의 id
     */
    public void decreaseBulletinNum(int id) {
        mapper.decreaseBulletinNum(id);
    }
}
