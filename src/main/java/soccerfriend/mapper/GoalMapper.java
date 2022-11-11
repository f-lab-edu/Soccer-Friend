package soccerfriend.mapper;

import org.apache.ibatis.annotations.Mapper;
import soccerfriend.dto.Goal;

import java.util.List;


@Mapper
public interface GoalMapper {

    public void insert(Goal goal);

    public Goal getGoalById(int id);

    public List<Goal> getGoalByMemberId(int memberId);
}
