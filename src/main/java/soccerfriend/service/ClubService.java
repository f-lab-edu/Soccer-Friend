package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Club;
import soccerfriend.exception.member.DuplicatedException;
import soccerfriend.mapper.ClubMapper;

import static soccerfriend.exception.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubMapper mapper;

    /**
     * 클럽을 생성합니다.
     *
     * @param id   클럽을 생성하는 member의 id
     * @param club 생성하려는 club의 기본정보
     */
    public void create(int id, Club club) {

        if (isNameExist(club.getName())) {
            throw new DuplicatedException(CLUB_NAME_DUPLICATED);
        }

        Club newClub = Club.builder()
                           .name(club.getName())
                           .leader(id)
                           .addressId(club.getAddressId())
                           .point(0)
                           .build();

        mapper.insert(newClub);
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
}
