package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.Stadium;
import soccerfriend.mapper.StadiumMapper;
import soccerfriend.utility.InputForm.UpdateStadiumRequest;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumMapper mapper;

    /**
     * stadiumOwner가 새로운 stadium을 생성합니다.
     *
     * @param stadiumOwnerId stadium을 생성하려는 stadiumOwner의 id
     * @param stadium        생성하려는 stadium의 정보
     */
    public void create(int stadiumOwnerId, Stadium stadium) {
        Stadium newStadium = Stadium.builder()
                                    .name(stadium.getName())
                                    .stadiumOwnerId(stadiumOwnerId)
                                    .address(stadium.getAddress())
                                    .phoneNumber(stadium.getPhoneNumber())
                                    .priceDay(stadium.getPriceDay())
                                    .priceNight(stadium.getPriceNight())
                                    .priceWeekend(stadium.getPriceWeekend())
                                    .build();

        mapper.insert(newStadium);
    }

    public boolean isStadiumOwner(int id, int stadiumOwnerId){
        return mapper.isStadiumOwner(id, stadiumOwnerId);
    }

    /**
     * 특정 id의 stadium을 반환합니다.
     *
     * @param id stadium의 id
     * @return 특정 id의 stadium 객체
     */
    public Stadium getStadiumById(int id) {
        return mapper.getStadiumById(id);
    }

    /**
     * 특정 stadiumOwner가 운영중인 모든 stadium들을 반환합니다.
     *
     * @param stadiumOwnerId stadiumOwner의 id
     * @return stadiumOwner가 운영중인 모든 stadium 객체들
     */
    public List<Stadium> getStadiumByStadiumOwnerId(int stadiumOwnerId) {
        return mapper.getStadiumByStadiumOwnerId(stadiumOwnerId);
    }

    /**
     * stadium의 소유자를 제외한 모든 정보를 수정합니다.
     *
     * @param id 수정하려는 stadium의 id
     * @param updateStadiumRequest 수정내용을 포함한 stadium 객체
     */
    public void updateStadium(int id, UpdateStadiumRequest updateStadiumRequest){
        mapper.updateStadium(id, updateStadiumRequest);
    }

    /**
     * stadium의 stadiumOwner를 변경합니다.
     *
     * @param id 수정하려는 stadium의 id
     * @param stadiumOwnerId 새로운 stadiumOwner의 id
     */
    public void updateStadiumOwner(int id, int stadiumOwnerId){
        mapper.updateStadiumOwner(id, stadiumOwnerId);
    }

}
