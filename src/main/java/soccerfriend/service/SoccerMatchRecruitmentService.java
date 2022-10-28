package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.mapper.SoccerMatchRecruitmentMapper;

@Service
@RequiredArgsConstructor
public class SoccerMatchRecruitmentService {

    private final SoccerMatchRecruitmentMapper mapper;

    public void create(int clubId, SoccerMatchRecruitment soccerMatchRecruitment) {
        SoccerMatchRecruitment newSoccerMatchRecruitment =
                SoccerMatchRecruitment.builder()
                                      .startTime(soccerMatchRecruitment.getStartTime())
                                      .endTime(soccerMatchRecruitment.getEndTime())
                                      .setTime(soccerMatchRecruitment.getSetTime())
                                      .setNumber(soccerMatchRecruitment.getSetNumber())
                                      .stadiumId(soccerMatchRecruitment.getStadiumId())
                                      .club1Id(clubId)
                                      .build();

        mapper.insert(newSoccerMatchRecruitment);
    }

}
