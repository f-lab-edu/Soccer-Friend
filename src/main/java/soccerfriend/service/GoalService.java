package soccerfriend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soccerfriend.dto.Goal;
import soccerfriend.dto.SoccerMatch;
import soccerfriend.dto.SoccerMatchMember;
import soccerfriend.dto.SoccerMatchRecruitment;
import soccerfriend.exception.exception.BadRequestException;
import soccerfriend.mapper.GoalMapper;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.*;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalMapper mapper;
    private final SoccerMatchMemberService soccerMatchMemberService;
    private final SoccerMatchService soccerMatchService;
    private final SoccerMatchRecruitmentService soccerMatchRecruitmentService;

    /**
     * 경기에서 넣은 골에 대한 정보를 추가합니다. 이 때 soccerMatch의 득점 정보가 동시에 변경됩니다.
     *
     * @param goal 골의 정보
     */
    @Transactional
    public void add(Goal goal) {
        SoccerMatchMember soccerMatchMember = soccerMatchMemberService.getSoccerMatchMemberById(goal.getSoccerMatchMemberId());
        SoccerMatch soccerMatch = soccerMatchService.getSoccerMatchById(soccerMatchMember.getSoccerMatchId());
        SoccerMatchRecruitment soccerMatchRecruitment = soccerMatchRecruitmentService.getSoccerMatchRecruitmentById(soccerMatch.getSoccerMatchRecruitmentId());
        int soccerMatchId = soccerMatchMember.getSoccerMatchId();
        int clubId = soccerMatchMember.getClubId();

        if (goal.getSetNumber() > soccerMatchRecruitment.getSetNumber() || goal.getSetNumber() < 1) {
            throw new BadRequestException(NOT_PROPER_GOAL);
        }
        if (goal.getSetTime() > soccerMatchRecruitment.getSetTime() || goal.getSetTime() < 0) {
            throw new BadRequestException(NOT_PROPER_GOAL);
        }

        mapper.insert(goal);
        if (soccerMatchRecruitment.getClub1Id() == clubId) {
            soccerMatchService.increaseClub1Score(soccerMatchId);
        }
        else if (soccerMatchRecruitment.getClub2Id() == clubId) {
            soccerMatchService.increaseClub2Score(soccerMatchId);
        }
        else {
            throw new BadRequestException(NOT_CLUB_OF_SOCCER_MATCH);
        }
    }

    /**
     * 특정 id의 goal을 반환합니다.
     *
     * @param id goal 의 id
     * @return 특정 id의 goal
     */
    public Goal getGoalById(int id) {
        Goal goal = mapper.getGoalById(id);
        if (goal == null) {
            throw new BadRequestException(GOAL_NOT_EXIST);
        }

        return goal;
    }

    /**
     * 특정 member가 지금까지 넣은 모든 goal에 대한 정보를 반환합니다.
     *
     * @param memberId
     * @return
     */
    public List<Goal> getGoalByMemberId(int memberId) {
        List<Goal> goal = mapper.getGoalByMemberId(memberId);
        if (goal.isEmpty()) {
            throw new BadRequestException(GOAL_NOT_EXIST);
        }

        return goal;
    }
}
