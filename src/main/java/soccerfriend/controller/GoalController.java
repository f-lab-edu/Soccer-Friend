package soccerfriend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soccerfriend.dto.Goal;
import soccerfriend.dto.SoccerMatchMember;
import soccerfriend.exception.exception.NoPermissionException;
import soccerfriend.service.*;

import java.util.List;

import static soccerfriend.exception.ExceptionInfo.NO_CLUB_PERMISSION;


@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class GoalController {

    private final ClubMemberService clubMemberService;
    private final SoccerMatchMemberService soccerMatchMemberService;
    private final AuthorizeService authorizeService;
    private final soccerfriend.service.GoalService goalService;


    /**
     * club의 운영진이 골에 대한 정보를 추가합니다.
     *
     * @param goal 골에 대한 정보
     */
    @PostMapping
    public void addGoal(@RequestBody Goal goal) {
        int memberId = authorizeService.getMemberId();
        int soccerMatchMemberId = goal.getSoccerMatchMemberId();
        SoccerMatchMember soccerMatchMember = soccerMatchMemberService.getSoccerMatchMemberById(soccerMatchMemberId);
        if (!clubMemberService.isClubLeaderOrStaff(soccerMatchMember.getClubId(), memberId)) {
            throw new NoPermissionException(NO_CLUB_PERMISSION);
        }

        goalService.add(goal);
    }

    /**
     * 특정 id의 골을 조회합니다.
     *
     * @param id goal의 id
     * @return
     */
    @GetMapping("/{id}")
    public Goal getGoalById(@PathVariable int id) {
        return goalService.getGoalById(id);
    }

    /**
     * 특정 member가 현재까지 넣은 모든 골에 대한 정보를 조회합니다.
     *
     * @param memberId member의 id
     * @return member가 지금까지 넣은 골
     */
    @GetMapping("/member/{memberId}")
    public List<Goal> getGoalByMemberId(@PathVariable int memberId) {
        return goalService.getGoalByMemberId(memberId);
    }
}