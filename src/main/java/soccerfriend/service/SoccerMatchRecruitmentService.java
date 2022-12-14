package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.SoccerMatchRecruitmentMapper;
import soccerfriend.utility.InputForm.UpdateSoccerMatchRecruitmentRequest;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class SoccerMatchRecruitmentService {

    private final SoccerMatchRecruitmentMapper mapper;

    /**
     * 축구경기 모집공고를 생성합니다.
     *
     * @param clubId                 경기모집 공고를 생성한 club의 id
     * @param soccerMatchRecruitment 경기에 관한 기본정보
     */
    public void create(int clubId, SoccerMatchRecruitment soccerMatchRecruitment) {
        SoccerMatchRecruitment newSoccerMatchRecruitment =
                SoccerMatchRecruitment.builder()
                                      .startTime(soccerMatchRecruitment.getStartTime())
                                      .endTime(soccerMatchRecruitment.getEndTime())
                                      .timeSet(soccerMatchRecruitment.getTimeSet())
                                      .numSet(soccerMatchRecruitment.getNumSet())
                                      .stadiumId(soccerMatchRecruitment.getStadiumId())
                                      .hostClubId(clubId)
                                      .build();

        mapper.insert(newSoccerMatchRecruitment);
    }

    /**
     * 특정 id의 soccerMatchRecruitment를 조회합니다.
     *
     * @param id soccerMatchRecruitment의 id
     * @return 특정 id의 soccerMatchRecruitment 객체
     */
    public SoccerMatchRecruitment getSoccerMatchRecruitmentById(int id) {
        SoccerMatchRecruitment soccerMatchRecruitment = mapper.getSoccerMatchRecruitmentById(id);
        if (soccerMatchRecruitment == null) {
            throw new BadRequestException(SOCCER_MATCH_RECRUITMENT_NOT_EXIST);
        }
        return soccerMatchRecruitment;
    }

    /**
     * 특정 club의 모든 soccerMatchRecruitment를 조회합니다.
     *
     * @param clubId club의 id
     * @return 특정 club이 참여한 모든 soccerMatchRecruitment
     */
    public List<SoccerMatchRecruitment> getSoccerMatchRecruitmentByClubId(int clubId) {
        List<SoccerMatchRecruitment> soccerMatchRecruitment = mapper.getSoccerMatchRecruitmentByClubId(clubId);
        if (soccerMatchRecruitment.isEmpty()) {
            throw new BadRequestException(SOCCER_MATCH_RECRUITMENT_NOT_EXIST);
        }
        return soccerMatchRecruitment;
    }

    /**
     * soccerMatchRecruitment의 정보를 수정합니다.
     *
     * @param id      soccerMatchRecruitment의 id
     * @param request 수정하고자 하는 값들
     */
    public void update(int id, UpdateSoccerMatchRecruitmentRequest request) {
        mapper.update(id, request);
    }

    /**
     * soccerMatchRecruitment를 보고 다른 클럽이 이를 승낙합니다. 즉 결투를 신청합니다.
     *
     * @param id                  soccerMatchRecruitment의 id
     * @param participationClubId 신청하려는 club의 id
     */
    public void setParticipationClubId(int id, int participationClubId) {
        SoccerMatchRecruitment soccerMatchRecruitment = getSoccerMatchRecruitmentById(id);
        if (isParticipationClubExist(id)) {
            throw new BadRequestException(ALREADY_MATCH_APPROVED);
        }
        if (soccerMatchRecruitment.getHostClubId() == participationClubId) {
            throw new BadRequestException(SAME_AS_HOST_CLUB);
        }

        mapper.setParticipationClubId(id, participationClubId);
    }

    /**
     * soccerMatchRecruitment의 상대 club이 정해졌는지 확인합니다.
     *
     * @param id soccerMatchRecruitment의 id
     * @return soccerMatchRecruitment의 상대 club이 정해졌는지 여부
     */
    public boolean isParticipationClubExist(int id) {
        return mapper.isParticipationClubExist(id);
    }
}
