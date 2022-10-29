package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.SoccerMatch;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.SoccerMatchMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.SOCCER_MATCH_NOT_EXIST;


@Service
@RequiredArgsConstructor
public class SoccerMatchService {

    private final SoccerMatchMapper mapper;

    /**
     * soccerMatch를 생성합니다. 이 때 생성된 soccerMatch는 점수가 0대 0인 초기 상황의 상태입니다.
     *
     * @param soccerMatchRecruitmentId soccerMatch가 이루어지기 위한 공고의 id
     */
    public void create(int soccerMatchRecruitmentId) {
        mapper.insert(soccerMatchRecruitmentId);
    }

    /**
     * 특정 id의 soccerMatch를 반환합니다.
     *
     * @param id soccerMatch의 id
     * @return 특정 id의 soccerMatch
     */
    public SoccerMatch getSoccerMatchById(int id) {
        SoccerMatch soccerMatch = mapper.getSoccerMatchById(id);
        if (soccerMatch == null) {
            throw new BadRequestException(SOCCER_MATCH_NOT_EXIST);
        }

        return soccerMatch;
    }

    /**
     * 특정 축구경기모집공고를 통해 생성된 soccerMatch를 반환합니다.
     *
     * @param soccerMatchRecruitmentId soccerMatch가 이루어지기 위한 공고의 id
     * @return 특정 축구경기모집공고를 통해 생성된 soccerMatch
     */
    public SoccerMatch getSoccerMatchBySoccerMatchRecruitmentId(int soccerMatchRecruitmentId) {
        SoccerMatch soccerMatch = mapper.getSoccerMatchBySoccerMatchRecruitmentId(soccerMatchRecruitmentId);
        if (soccerMatch == null) {
            throw new BadRequestException(SOCCER_MATCH_NOT_EXIST);
        }

        return soccerMatch;
    }

    /**
     * 특정 club이 참여한 모든 soccerMatch를 반환합니다.
     *
     * @param clubId club의 id
     * @return 특정 club이 참여한 모든 soccerMatch
     */
    public List<SoccerMatch> getSoccerMatchByClubId(int clubId) {
        List<SoccerMatch> soccerMatch = mapper.getSoccerMatchByClubId(clubId);
        if (soccerMatch.isEmpty()) {
            throw new BadRequestException(SOCCER_MATCH_NOT_EXIST);
        }

        return soccerMatch;
    }

    /**
     * soccerMatch의 club1의 점수를 1 증가시킵니다.
     *
     * @param id soccerMatch의 id
     */
    public void increaseClub1Score(int id) {
        mapper.increaseClub1Score(id);
    }

    /**
     * soccerMatch의 club1의 점수를 1 감소시킵니다.
     *
     * @param id soccerMatch의 id
     */
    public void increaseClub2Score(int id) {
        mapper.increaseClub2Score(id);
    }
}
