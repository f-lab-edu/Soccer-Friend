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

    @GetMapping("/{id}")
    public Goal getGoalById(@PathVariable int id) {
        return goalService.getGoalById(id);
    }

    @GetMapping("/member/{memberId}")
    public List<Goal> getGoalByMemberId(@PathVariable int memberId) {
        return goalService.getGoalByMemberId(memberId);
    }
}