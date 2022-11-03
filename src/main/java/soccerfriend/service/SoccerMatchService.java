package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soccerfriend.dto.SoccerMatch;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.SoccerMatchMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;


@Service
@RequiredArgsConstructor
public class SoccerMatchService {

    private final SoccerMatchMapper mapper;
    private final ClubSoccerMatchRecordService clubSoccerMatchRecordService;

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

    /**
     * 해당 soccerMatch가 존재하는지 확인합니다.
     *
     * @param id     soccerMatch의 id
     * @param clubId club의 id
     * @return
     */
    public boolean isClubExist(int id, int clubId) {
        return mapper.isClubExist(id, clubId);
    }

    /**
     * soccerMatch의 club1의 id를 반환합니다.
     *
     * @param id soccerMatch의 id
     * @return club1의 id
     */
    public int getClub1Id(int id) {
        return mapper.getClub1Id(id);
    }

    /**
     * soccerMatch의 club2의 id를 반환합니다.
     *
     * @param id soccerMatch의 id
     * @return club2의 id
     */
    public int getClub2Id(int id) {
        return mapper.getClub2Id(id);
    }

    public int getClub1Score(int id) {
        return mapper.getClub1Score(id);
    }

    public int getClub2Score(int id) {
        return mapper.getClub2Score(id);
    }

    /**
     * 경기결과 기입을 완료하여 이를 제출하고 전적에 반영합니다.
     *
     * @param id soccerMatch의 id
     */
    public void submit(int id) {
        if (isSubmitted(id)) {
            throw new BadRequestException(ALREADY_SUBMITTED_MATCH);
        }

        int club1Score = getClub1Score(id);
        int club2Score = getClub2Score(id);
        int club1Id = getClub1Id(id);
        int club2Id = getClub2Id(id);

        if (club1Score > club2Score) {
            clubSoccerMatchRecordService.increaseWin(club1Id);
            clubSoccerMatchRecordService.increaseLose(club2Id);
        }
        else if (club1Score == club2Score) {
            clubSoccerMatchRecordService.increaseDraw(club1Id);
            clubSoccerMatchRecordService.increaseDraw(club2Id);
        }
        else {
            clubSoccerMatchRecordService.increaseLose(club1Id);
            clubSoccerMatchRecordService.increaseWin(club2Id);
        }

        setSubmittedTrue(id);
    }

    /**
     * 해당 soccerMatch의 성적 반영처리 여부인 submitted를 true로 바꿉니다.
     *
     * @param id soccerMatch의 id
     */
    public void setSubmittedTrue(int id) {
        mapper.setSubmittedTrue(id);
    }

    /** 해당 soccerMatch의 성적반영처리 여부를 반환합니다.
     *
     * @param id soccerMatch의 id
     * @return 성적반영처리 여부
     */
    public boolean isSubmitted(int id) {
        return mapper.isSubmitted(id);
    }
}
