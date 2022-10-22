package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Club;
import soccerfriend.dto.ClubMember;
import soccerfriend.exception.exception.DuplicatedException;
import soccerfriend.exception.exception.NotExistException;
import soccerfriend.mapper.ClubMapper;

import static soccerfriend.dto.ClubMember.ClubMemberGrade.MEMBER;
import static soccerfriend.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubMapper clubMapper;
    private final ClubMemberService clubMemberService;

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

        if (!isIdExist(clubId)) {
            throw new NotExistException(CLUB_NAME_DUPLICATED);
        }

        clubMemberService.add(clubId, memberId);
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
}
