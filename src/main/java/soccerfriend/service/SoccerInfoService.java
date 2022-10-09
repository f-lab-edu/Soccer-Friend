package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.SoccerInfo;
import soccerfriend.mapper.SoccerInfoMapper;


@Service
@RequiredArgsConstructor
public class SoccerInfoService {

    private final SoccerInfoMapper soccerInfoMapper;

    /**
     * soccerInfo를 생성합니다.
     *
     * @param soccerInfo addressId, positionsId를 포함하는 soccerInfo 객체
     * @return 셍상힌 soccerInfo의 id
     */
    public int insert(SoccerInfo soccerInfo) {
        soccerInfoMapper.insert(soccerInfo);
        return soccerInfo.getId();
    }
}
